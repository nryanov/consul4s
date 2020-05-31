package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog._
import consul4s.model.health.NewHealthCheck
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class CatalogBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "catalog" should {
    "return datacenters" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.getDatacenters().body
        } yield {
          assertResult(List("dc1"))(result)
        }
      }
    }

    "return nodes" in withContainers { consul =>
      val client = createClient(consul)

      runEitherEventually {
        for {
          result <- client.getDatacenterNodes().body
        } yield {
          assert(result.head.datacenter.contains("dc1"))
        }
      }
    }

    "return services" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.getDatacenterServiceNames().body
        } yield {
          assertResult(Set("consul"))(result.keySet)
        }
      }
    }

    "register, get info and deregister node" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = NodeRegistration("node", "address", check = Some(NewHealthCheck("node", "nodeCheck")))
      val deleteNode = NodeDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result <- client.getDatacenterNodes().body
          _ <- client.deregisterEntity(deleteNode).body
          afterDeregistration <- client.getDatacenterNodes().body
        } yield {
          assert(result.exists(_.node == "node"))
          assert(!afterDeregistration.exists(_.node == "node"))
        }
      }
    }

    "register, get info and deregister service" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = NodeRegistration("node", "address", service = Some(NewCatalogService("testService")))
      val deleteNode = NodeDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result1 <- client.getDatacenterServices("testService").body
          _ <- client.deregisterEntity(deleteNode).body
          result2 <- client.getDatacenterServices("testService").body
        } yield {
          assert(result1.exists(_.serviceName == "testService"))
          assert(!result2.exists(_.serviceName == "testService"))
        }
      }
    }

    "register, get info about node and deregister" in withContainers { consul =>
      val client = createClient(consul)

      val registerNode = NodeRegistration("node", "address", service = Some(NewCatalogService("testService")))
      val deleteNode = NodeDeregistration("node")

      runEither {
        for {
          _ <- client.registerEntity(registerNode).body
          result1 <- client.getListOfNodeServices("node").body
          result2 <- client.getMapOfNodeServices("node").body
          _ <- client.deregisterEntity(deleteNode).body
          result3 <- client.getListOfNodeServices("node").body
          result4 <- client.getMapOfNodeServices("node").body
        } yield {
          assert(result1.exists(_.node.node == "node") && result1.exists(_.services.exists(_.service == "testService")))
          assert(result2.exists(_.node.node == "node") && result2.exists(_.services.contains("testService")))
          assert(result3.isEmpty)
          assert(result4.isEmpty)
        }
      }
    }
  }
}
