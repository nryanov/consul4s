package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.State
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder}

// todo: use catalog api to get nodes/services
abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "health" should {
    "return node checks" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.nodeChecks("node").body

      assert(true)
    }

    "return service checks" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.serviceChecks("service").body

      assert(true)
    }

    "return checks in state" in withContainers { consul =>
      val client = createClient(consul)

      val result = client.checksInState(State.Passing).body

      assert(true)
    }
  }
}
