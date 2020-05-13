package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class CatalogBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "catalog" should {
    "return datacenters" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.datacenters().body
      assertResult(List("dc1"))(result)
    }

    "return nodes" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.nodes().body
      assertResult("dc1")(result.head.datacenter)
    }

    "return services" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.services().body
      assertResult(Set("consul"))(result.keySet)
    }
  }
}
