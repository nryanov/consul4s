package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.CheckStatus
import consul4s.model.agent.{NewService, ServiceTTLCheck}
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "health" should {
    "return node checks" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          node <- client.getDatacenterNodes().body
          result <- client.getNodeChecks(node.head.node).body
        } yield {
          assertResult(CheckStatus.Passing)(result.head.status)
        }
      }
    }

    "return service checks" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService", checks = Some(List(ServiceTTLCheck("ttlCheck", "15s"))))

      runEither {
        for {
          _ <- client.registerAgentService(newService).body
          result <- client.getServiceChecks("testService").body
        } yield {
          assert(result.exists(_.serviceId == "testService"))
        }
      }
    }

    "return checks in state" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.getChecksByState(CheckStatus.Passing).body
        } yield {
          assertResult(CheckStatus.Passing)(result.head.status)
        }
      }
    }

    "return nodes for service" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          service <- client.getDatacenterServiceNames().body
          result <- client.getAllServiceInstances(service.head._1).body
        } yield {
          assert(result.length == 1)
        }
      }
    }

    "return nodes for connect capable service" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          service <- client.getDatacenterServiceNames().body
          result <- client.getNodesForConnectCapableService(service.head._1).body
        } yield {
          assert(result.isEmpty)
        }
      }
    }
  }
}
