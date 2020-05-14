package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.agent._
import consul4s.model.catalog._
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
      assert(result.head.Datacenter.contains("dc1"))
    }

    "return services" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.services().body
      assertResult(Set("consul"))(result.keySet)
    }

    "register, get info and deregister node" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address")
      val deleteNode = EntityDeregistration("node")

      client.registerEntity(registerNode)

      val result = client.nodes().body
      assert(result.exists(_.Node == "node"))

      client.deregisterEntity(deleteNode)

      val afterDeregistration = client.nodes().body
      assert(!afterDeregistration.exists(_.Node == "node"))
    }
  }
}
