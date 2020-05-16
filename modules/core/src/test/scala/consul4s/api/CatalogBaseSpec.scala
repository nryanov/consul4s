package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog._
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class CatalogBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "catalog" should {
    "return datacenters" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.datacenters().body
        } yield {
          assertResult(List("dc1"))(result)
        }
      }
    }

    "return nodes" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.nodes().body
        } yield {
          assert(result.head.Datacenter.contains("dc1"))
        }
      }
    }

    "return services" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.services().body
        } yield {
          assertResult(Set("consul"))(result.keySet)
        }
      }
    }

    "register, get info and deregister node" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address")
      val deleteNode = EntityDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result <- client.nodes().body
          _ <- client.deregisterEntity(deleteNode).body
          afterDeregistration <- client.nodes().body
        } yield {
          assert(result.exists(_.Node == "node"))
          assert(!afterDeregistration.exists(_.Node == "node"))
        }
      }
    }

    "register, get info and deregister service" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address", Service = Some(Service("testService")))
      val deleteNode = EntityDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result1 <- client.nodesInfoForService("testService").body
          _ <- client.deregisterEntity(deleteNode).body
          result2 <- client.nodesInfoForService("testService").body
        } yield {
          assert(result1.exists(_.ServiceName == "testService"))
          assert(!result2.exists(_.ServiceName == "testService"))
        }
      }
    }

    "register, get info about node and deregister" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = EntityRegistration("node", "address", Service = Some(Service("testService")))
      val deleteNode = EntityDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result1 <- client.listOfServicesForNode("node").body
          result2 <- client.mapOfServicesForNode("node").body
          _ <- client.deregisterEntity(deleteNode).body
          result3 <- client.listOfServicesForNode("node").body
          result4 <- client.mapOfServicesForNode("node").body
        } yield {
          assert(result1.Node.exists(_.Node == "node") && result1.Services.exists(_.exists(_.Service == "testService")))
          assert(result2.exists(_.Node.Node == "node") && result2.exists(_.Services.contains("testService")))
          assert(result3.Node.isEmpty)
          assert(result4.isEmpty)
        }
      }
    }
  }
}
