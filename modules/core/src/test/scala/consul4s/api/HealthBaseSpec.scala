package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.State
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder}

abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "health" should {
    "return node checks" in withContainers { consul =>
      val client = createClient(consul)

      val node = client.nodes().body.head
      val result = client.nodeChecks(node.node).body

      assertResult(State.Passing)(result.head.status)
    }

    // todo: register service and get check
    "return service checks" in withContainers { consul =>
      val client = createClient(consul)

      val service = client.services().body
      val result = client.serviceChecks(service.head._1).body

      assert(true)
    }

    "return checks in state" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.checksInState(State.Passing).body

      assertResult(State.Passing)(result.head.status)
    }

    "return nodes for service" in withContainers { consul =>
      val client = createClient(consul)

      val service = client.services().body
      val result = client.nodesForService(service.head._1).body

      assert(result.length == 1)
    }

    "return nodes for connect capable service" in withContainers { consul =>
      val client = createClient(consul)

      val service = client.services().body
      val result = client.nodesForConnectCapableService(service.head._1).body

      assert(result.isEmpty)
    }
  }
}
