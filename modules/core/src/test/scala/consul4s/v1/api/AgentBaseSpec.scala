package consul4s.v1.api

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
          info <- client.getAgentMembers().body
        } yield assert(true)
      }
    }

    "register check, get info and deregister it" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.registerAgentCheck(check).body
          r1 <- client.getAgentChecks().body
          _ <- client.deregisterAgentCheck("testTTLCheck").body
          r2 <- client.getAgentChecks().body
        } yield {
          assert(r1.contains("testTTLCheck"))
          assert(r1("testTTLCheck").status == CheckStatus.Critical)
          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to pass" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.registerAgentCheck(check).body
          _ <- client.setAgentTtlCheckPass("testTTLCheck", Some("manual update to pass")).body
          r1 <- client.getAgentChecks().body
          _ <- client.deregisterAgentCheck("testTTLCheck").body
          r2 <- client.getAgentChecks().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.status == CheckStatus.Passing)
          assert(checkInfo.output == "manual update to pass")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to warn" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.registerAgentCheck(check).body
          _ <- client.setAgentTtlCheckWarn("testTTLCheck", Some("manual update to warn")).body
          r1 <- client.getAgentChecks().body
          _ <- client.deregisterAgentCheck("testTTLCheck").body
          r2 <- client.getAgentChecks().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.status == CheckStatus.Warning)
          assert(checkInfo.output == "manual update to warn")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status to fail" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.registerAgentCheck(check).body
          _ <- client.setAgentTtlCheckFail("testTTLCheck", Some("manual update to fail")).body
          r1 <- client.getAgentChecks().body
          _ <- client.deregisterAgentCheck("testTTLCheck").body
          r2 <- client.getAgentChecks().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.status == CheckStatus.Critical)
          assert(checkInfo.output == "manual update to fail")

          assert(r2.isEmpty)
        }
      }
    }

    "register check and manually update status using update api" in withContainers { consul =>
      val client = createClient(consul)
      val check = TTLCheck("testTTLCheck", "15s")

      runEither {
        for {
          _ <- client.registerAgentCheck(check).body
          _ <- client.updateAgentTtlCheck("testTTLCheck", CheckUpdate(CheckStatus.Passing, Some("manual update to pass"))).body
          r1 <- client.getAgentChecks().body
          _ <- client.deregisterAgentCheck("testTTLCheck").body
          r2 <- client.getAgentChecks().body
        } yield {
          assert(r1.contains("testTTLCheck"))

          val checkInfo = r1("testTTLCheck")

          assert(checkInfo.status == CheckStatus.Passing)
          assert(checkInfo.output == "manual update to pass")

          assert(r2.isEmpty)
        }
      }
    }

    "register service, return info and delete it" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService")

      val expectedService = Service(
        service = "testService",
        id = "testService",
        tags = Some(List()),
        address = "",
        taggedAddresses = None,
        meta = Some(Map()),
        port = 0,
        enableTagOverride = false,
        weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.registerAgentService(newService).body
          services <- client.getAgentServices().body
          serviceInfo <- client.getAgentService("testService").body
          _ <- client.deregisterAgentService("testService").body
          servicesAfterDeletion <- client.getAgentServices().body
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
        check = Some(ServiceTTLCheck("testTTLCheck1", "5s")),
        checks = Some(
          List(
            ServiceTTLCheck("testTTLCheck2", "15s"),
            ServiceTTLCheck("testTTLCheck3", "30s")
          )
        )
      )

      val expectedService = Service(
        service = "testService",
        id = "testService",
        tags = Some(List()),
        address = "",
        taggedAddresses = None,
        meta = Some(Map()),
        port = 0,
        enableTagOverride = false,
        weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.registerAgentService(newService).body
          services <- client.getAgentServices().body
          aggregatedServiceInfoById <- client.getAgentLocalServiceHealthById("testService").body
          aggregatedServiceInfoByName <- client.getAgentLocalServiceHealthByName("testService").body
          _ <- client.deregisterAgentService("testService").body
          servicesAfterDeletion <- client.getAgentServices().body
        } yield {
          assert(aggregatedServiceInfoById.exists(_.service == expectedService))
          assert(aggregatedServiceInfoByName.exists(_.exists(_.service == expectedService)))

          assert(aggregatedServiceInfoById.exists(_.checks.length == 3))
          assert(aggregatedServiceInfoByName.exists(_.exists(_.checks.length == 3)))

          assert(services.contains("testService"))
          assert(!servicesAfterDeletion.contains("testService"))
        }
      }
    }

    "register service and enable maintenance mode" in withContainers { consul =>
      val client = createClient(consul)
      val newService = NewService("testService")

      val expectedService = Service(
        service = "testService",
        id = "testService",
        tags = Some(List()),
        address = "",
        taggedAddresses = None,
        meta = Some(Map()),
        port = 0,
        enableTagOverride = false,
        weights = Weights(1, 1)
      )

      runEither {
        for {
          _ <- client.registerAgentService(newService).body
          services <- client.getAgentServices().body
          _ <- client.setAgentServiceMaintenanceMode("testService", enable = true).body
          aggregatedServiceInfoById <- client.getAgentLocalServiceHealthById("testService").body
          _ <- client.deregisterAgentService("testService").body
          servicesAfterDeletion <- client.getAgentServices().body
        } yield {
          assert(aggregatedServiceInfoById.exists(_.service == expectedService))
          assert(aggregatedServiceInfoById.exists(_.aggregatedStatus == CheckStatus.Maintenance))

          assert(services.contains("testService"))
          assert(!servicesAfterDeletion.contains("testService"))
        }
      }
    }
  }
}
