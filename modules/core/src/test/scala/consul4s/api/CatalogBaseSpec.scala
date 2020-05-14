package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.agent.AgentService
import consul4s.model.catalog.{CatalogDeregistration, CatalogRegistration}
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
      assertResult("dc1")(result.head.Datacenter)
    }

    "return services" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.services().body
      assertResult(Set("consul"))(result.keySet)
    }

    "register, get info and deregister entity" in withContainers { consul =>
      val client = createClient(consul)

      val registration = CatalogRegistration("node", "address", AgentService("testService"))
      val deregistration = CatalogDeregistration("node", None, Some("testService"))
      client.registerEntity(registration)

      val result = client.services().body
      assert(result.contains("testService"))

      val serviceInfo = client.nodesInfoForService("testService").body

      client.deregisterEntity(deregistration)

      val afterDeregistration = client.services().body
      assert(!afterDeregistration.contains("testService"))
      assertResult(serviceInfo.head.ServiceName)("testService")
    }
  }
}
