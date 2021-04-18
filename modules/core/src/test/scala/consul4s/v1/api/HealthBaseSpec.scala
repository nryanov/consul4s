package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.CheckStatus
import consul4s.model.agent.{NewService, ServiceTTLCheck}
import consul4s.{ConsistencyMode, ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "health" should {
    "return node checks" in withContainers { consul =>
      val client = createClient(consul)

      runEitherEventually {
        for {
          nodes <- client.getDatacenterNodes().body
          result <- client.getNodeChecks(nodes.head.Node, consistencyMode = ConsistencyMode.Consistent).body
        } yield assertResult(CheckStatus.Passing)(result.head.Status)
      }
    }

    "return service checks" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService", Checks = Some(List(ServiceTTLCheck("ttlCheck", "15s"))))

      runEitherEventually {
        for {
          _ <- client.registerAgentService(newService).body
          result <- client.getServiceChecks("testService", consistencyMode = ConsistencyMode.Consistent).body
          _ <- client.deregisterAgentService("testService").body
        } yield assert(result.exists(_.ServiceID == "testService"))
      }
    }

    "return checks in state" in withContainers { consul =>
      val client = createClient(consul)

      runEitherEventually {
        for {
          result <- client.getChecksByState(CheckStatus.Passing, consistencyMode = ConsistencyMode.Consistent).body
        } yield assertResult(CheckStatus.Passing)(result.head.Status)
      }
    }

    "return nodes for service" in withContainers { consul =>
      val client = createClient(consul)

      runEitherEventually {
        for {
          service <- client.getDatacenterServiceNames().body
          result <- client.getAllServiceInstances(service.head._1, consistencyMode = ConsistencyMode.Consistent).body
        } yield assert(result.length == 1)
      }
    }

    "return nodes for connect capable service" in withContainers { consul =>
      val client = createClient(consul)

      runEitherEventually {
        for {
          service <- client.getDatacenterServiceNames(consistencyMode = ConsistencyMode.Consistent).body
          result <- client.getNodesForConnectCapableService(service.head._1).body
        } yield assert(result.isEmpty)
      }
    }
  }
}
