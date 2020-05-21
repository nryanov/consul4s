package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.CheckStatus
import consul4s.model.agent.{NewService, TTLCheck}
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "health" should {
    "return node checks" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          node <- client.nodes().body
          result <- client.nodeChecks(node.head.node).body
        } yield {
          assertResult(CheckStatus.Passing)(result.head.status)
        }
      }
    }

    "return service checks" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService", checks = Some(List(TTLCheck("ttlCheck", "15s"))))

      runEither {
        for {
          _ <- client.agentRegisterLocalService(newService).body
          result <- client.serviceChecks("testService").body
        } yield {
          assert(result.exists(_.serviceId == "testService"))
        }
      }
    }

    "return checks in state" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.checksInState(CheckStatus.Passing).body
        } yield {
          assertResult(CheckStatus.Passing)(result.head.status)
        }
      }
    }

    "return nodes for service" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          service <- client.services().body
          result <- client.nodesForService(service.head._1).body
        } yield {
          assert(result.length == 1)
        }
      }
    }

    "return nodes for connect capable service" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          service <- client.services().body
          result <- client.nodesForConnectCapableService(service.head._1).body
        } yield {
          assert(result.isEmpty)
        }
      }
    }
  }
}
