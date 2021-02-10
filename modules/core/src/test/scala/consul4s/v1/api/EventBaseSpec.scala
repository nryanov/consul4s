package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

class EventBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "event api" should {
    "fire event" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result1 <- client.fireEvent("test", "payload").body
          result2 <- client.getEvents().body
        } yield assert(result2.exists(_.name == result1.name))
      }
    }

    "return empty list of events because (filtered by name)" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          result <- client.getEvents(name = Some("notExistingEvent")).body
        } yield assert(result.isEmpty)
      }
    }
  }
}
