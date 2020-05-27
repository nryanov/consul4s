package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class StatusBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "status" should {
    "return leader" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          leader <- client.getRaftLeader().body
        } yield {
          assertResult("127.0.0.1:8300")(leader)
        }
      }
    }

    "return peers" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          peers <- client.getRaftPeers().body
        } yield {
          assertResult(List("127.0.0.1:8300"))(peers)
        }
      }
    }
  }
}
