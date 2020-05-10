package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{BaseSpec, ConsulClient, ConsulContainer, JsonDecoder}
import sttp.client.{HttpURLConnectionBackend, Identity}

abstract class StatusBaseSpec(implicit jsonDecoder: JsonDecoder) extends BaseSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "status" should {
    "return leader" in withContainers { consul =>
      val client = createClient(consul)

      val leader = client.raftLeader().body

      assertResult("127.0.0.1:8300")(leader)
    }

    "return peers" in withContainers { consul =>
      val client = createClient(consul)

      val peers = client.raftPeers().body

      assertResult(List("127.0.0.1:8300"))(peers)
    }
  }

  private def createClient(consul: ConsulContainer): Status[Identity] = {
    val backend = HttpURLConnectionBackend()
    val client = ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}/v1", backend)

    client.status()
  }
}
