package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder}

abstract class StatusBaseSpec(implicit jsonDecoder: JsonDecoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "status" should {
    "return leader" in withContainers { consul =>
      val client = createClient(consul)

      val leader = client.raftLeader().body

      assertResult("127.0.0.1:8300")(leader)
    }

    "return peers" in withContainers { consul =>
      val client = createClient(consul)

      val peers = client.raftPeers().body

      assertResult(List("127.0.0.1:8300"))(peers)
    }
  }
}
