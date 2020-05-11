package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder}

abstract class AgentBaseSpec(implicit jsonDecoder: JsonDecoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "agent" should {
    "return member info" in withContainers { consul =>
      val client = createClient(consul)

      val nodes = client.nodes().body
      val result = client.members().body

      assert(nodes.length == result.length)
    }
  }
}
