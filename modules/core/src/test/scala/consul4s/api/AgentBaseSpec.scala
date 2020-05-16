package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.CheckStatus
import consul4s.model.agent._
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class AgentBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "agent" should {
    "return member info" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          info <- client.agentMembers().body
        } yield {
          assert(true)
        }
      }
    }

    "register check, get info and deregister it" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.agentRegisterCheck(check).body
          r1 <- client.agentCheckList().body
          _ <- client.agentDeregisterCheck("testTTLCheck").body
          r2 <- client.agentCheckList().body
        } yield {
          assert(r1.contains("testTTLCheck"))
          assert(r1("testTTLCheck").Status == CheckStatus.Critical)
          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to pass" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.agentRegisterCheck(check).body
          _ <- client.agentTTLCheckPass("testTTLCheck", Some("manual update to pass")).body
          r1 <- client.agentCheckList().body
          _ <- client.agentDeregisterCheck("testTTLCheck").body
          r2 <- client.agentCheckList().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.Status == CheckStatus.Passing)
          assert(checkInfo.Output == "manual update to pass")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to warn" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.agentRegisterCheck(check).body
          _ <- client.agentTTLCheckWarn("testTTLCheck", Some("manual update to warn")).body
          r1 <- client.agentCheckList().body
          _ <- client.agentDeregisterCheck("testTTLCheck").body
          r2 <- client.agentCheckList().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.Status == CheckStatus.Warning)
          assert(checkInfo.Output == "manual update to warn")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to fail" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.agentRegisterCheck(check).body
          _ <- client.agentTTLCheckFail("testTTLCheck", Some("manual update to fail")).body
          r1 <- client.agentCheckList().body
          _ <- client.agentDeregisterCheck("testTTLCheck").body
          r2 <- client.agentCheckList().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.Status == CheckStatus.Critical)
          assert(checkInfo.Output == "manual update to fail")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status using update api" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.agentRegisterCheck(check).body
          _ <- client.agentTTLCheckUpdate("testTTLCheck", CheckUpdate(CheckStatus.Passing, Some("manual update to pass"))).body
          r1 <- client.agentCheckList().body
          _ <- client.agentDeregisterCheck("testTTLCheck").body
          r2 <- client.agentCheckList().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.Status == CheckStatus.Passing)
          assert(checkInfo.Output == "manual update to pass")

          assert(r2.isEmpty)
        }
      }
    }
  }
}
