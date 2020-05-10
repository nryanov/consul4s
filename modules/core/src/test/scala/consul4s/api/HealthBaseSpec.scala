package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.State
import consul4s.{BaseSpec, ConsulClient, ConsulContainer, JsonDecoder}
import sttp.client.{HttpURLConnectionBackend, Identity}

// todo: use catalog api to get nodes/services
abstract class HealthBaseSpec(implicit jsonDecoder: JsonDecoder) extends BaseSpec with TestContainerForAll {
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

  private def createClient(consul: ConsulContainer): Health[Identity] = {
    val backend = HttpURLConnectionBackend()
    val client = ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}/v1", backend)

    client.health()
  }
}
