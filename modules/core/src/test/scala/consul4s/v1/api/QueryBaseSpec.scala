package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.query.QueryResult
import consul4s.model.query.QueryResult._
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}
import sttp.client._

class QueryBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "query" should {
    "return none if query does not exist" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          r <- client.executeQuery("notExisting").body
        } yield assert(r.isEmpty)
      }
    }

    "return query result" in withContainers { consul =>
      val client = createClient(consul)

      val backend = HttpURLConnectionBackend()
      val body =
        """
          |{
          |  "Name": "my-query",
          |  "Token": "",
          |  "Service": {
          |    "Service": "redis",
          |    "Failover": {
          |      "NearestN": 3,
          |      "Datacenters": ["dc1", "dc2"]
          |    },
          |    "Near": "node1",
          |    "OnlyPassing": false,
          |    "Tags": ["primary", "!experimental"],
          |    "NodeMeta": { "instance_type": "m3.large" },
          |    "ServiceMeta": { "environment": "production" }
          |  },
          |  "DNS": {
          |    "TTL": "10s"
          |  }
          |}
          |""".stripMargin

      val url = s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}/v1"
      val request = basicRequest.post(uri"$url/query").body(body)

      val queryId = backend.send(request).body.getOrElse(throw new RuntimeException("Error while getting id of prepared query"))

      val idx1 = queryId.indexOf(": \"") + 3
      val idx2 = queryId.indexOf("\"", idx1)

      val uuid = queryId.substring(idx1, idx2)

      val expectedResult = QueryResult("redis", "dc1", DNS("10s"), List())

      runEither {
        for {
          r <- client.executeQuery(uuid).body
        } yield assert(r.contains(expectedResult))
      }
    }
  }
}
