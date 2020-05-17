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

    "register service, return info and delete it" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService")

      val expectedService = Service(
        Service = "testService",
        ID = "testService",
        Tags = List(),
        Address = "",
        TaggedAddresses = None,
        Meta = Some(Map()),
        Port = 0,
        EnableTagOverride = false,
        Weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.agentRegisterLocalService(newService).body
          services <- client.agentServices().body
          serviceInfo <- client.agentService("testService").body
          _ <- client.agentDeregisterService("testService").body
          servicesAfterDeletion <- client.agentServices().body
        } yield {
          assert(services.contains("testService"))
          assert(!servicesAfterDeletion.contains("testService"))
          assert(serviceInfo.contains(expectedService))
        }
      }
    }

    "register service with checks, return health info and delete it" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService(
        "testService",
        Checks = Some(
          List(
            TTLCheck("testTTLCheck1", "15s"),
            TTLCheck("testTTLCheck2", "30s")
          )
        )
      )

      val expectedService = Service(
        Service = "testService",
        ID = "testService",
        Tags = List(),
        Address = "",
        TaggedAddresses = None,
        Meta = Some(Map()),
        Port = 0,
        EnableTagOverride = false,
        Weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.agentRegisterLocalService(newService).body
          services <- client.agentServices().body
          aggregatedServiceInfoById <- client.agentLocalServiceHealthById("testService").body
          aggregatedServiceInfoByName <- client.agentLocalServiceHealthByName("testService").body
          _ <- client.agentDeregisterService("testService").body
          servicesAfterDeletion <- client.agentServices().body
        } yield {
          assert(aggregatedServiceInfoById.exists(_.Service == expectedService))
          assert(aggregatedServiceInfoByName.exists(_.exists(_.Service == expectedService)))

          assert(services.contains("testService"))
          assert(!servicesAfterDeletion.contains("testService"))
        }
      }
    }

    "register service and enable maintenance mode" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService")

      val expectedService = Service(
        Service = "testService",
        ID = "testService",
        Tags = List(),
        Address = "",
        TaggedAddresses = None,
        Meta = Some(Map()),
        Port = 0,
        EnableTagOverride = false,
        Weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.agentRegisterLocalService(newService).body
          services <- client.agentServices().body
          _ <- client.agentEnableMaintenanceMode("testService", enable = true).body
          aggregatedServiceInfoById <- client.agentLocalServiceHealthById("testService").body
          _ <- client.agentDeregisterService("testService").body
          servicesAfterDeletion <- client.agentServices().body
        } yield {
          assert(aggregatedServiceInfoById.exists(_.Service == expectedService))
          assert(aggregatedServiceInfoById.exists(_.AggregatedStatus == CheckStatus.Maintenance))

          assert(services.contains("testService"))
          assert(!servicesAfterDeletion.contains("testService"))
        }
      }
    }
  }
}
