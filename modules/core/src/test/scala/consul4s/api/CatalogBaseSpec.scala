package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
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

    "register, get info and deregister service" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address", Service = Some(Service("testService")))
      val deleteNode = EntityDeregistration("node")

      client.registerEntity(registerNode)

      val result1 = client.nodesInfoForService("testService").body
      assert(result1.exists(_.ServiceName == "testService"))

      client.deregisterEntity(deleteNode)

      val result2 = client.nodesInfoForService("testService").body
      assert(!result2.exists(_.ServiceName == "testService"))
    }

    "register, get info about node and deregister" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address", Service = Some(Service("testService")))
      val deleteNode = EntityDeregistration("node")

      client.registerEntity(registerNode)

      val result1 = client.listOfServicesForNode("node").body
      val result2 = client.mapOfServicesForNode("node").body

      assert(result1.Node.exists(_.Node == "node") && result1.Services.exists(_.exists(_.Service == "testService")))
      assert(result2.exists(_.Node.Node == "node") && result2.exists(_.Services.contains("testService")))

      client.deregisterEntity(deleteNode)

      val result3 = client.listOfServicesForNode("node").body
      val result4 = client.mapOfServicesForNode("node").body

      assert(result3.Node.isEmpty)
      assert(result4.isEmpty)
    }
  }
}
