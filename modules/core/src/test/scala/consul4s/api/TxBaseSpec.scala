package consul4s.api

import java.nio.charset.StandardCharsets
import java.util.Base64

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog.{NewCatalogService, NodeRegistration}
import consul4s.model.health.NewHealthCheck
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import consul4s.model.transaction._
import consul4s.model.transaction.TxTask._
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

class TxBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "tx" should {
    "create KVPair" in withContainers { consul =>
      val client = createClient(consul)
      val value = new String(Base64.getEncoder.encode("value".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
      val txTask = TxTask(
        kv = Some(
          KVTask(
            verb = KVOp.Set,
            key = "key",
            value = Some(value)
          )
        )
      )

      runEither {
        for {
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.errors.isEmpty)
          assert(result.results.flatMap(_.headOption).flatMap(_.kv).exists(_.key == "key"))
        }
      }
    }

    "create Node" in withContainers { consul =>
      val client = createClient(consul)
      val txTask = TxTask(
        node = Some(
          NodeTask(
            verb = NodeOp.Set,
            node = NodeDefinition(
              node = "testNode",
              address = "localhost"
            )
          )
        )
      )

      runEither {
        for {
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.results.flatMap(_.headOption).flatMap(_.node).exists(_.Node == "testNode"))
          assert(result.errors.isEmpty)
        }
      }
    }

    "create Service" in withContainers { consul =>
      val client = createClient(consul)
      val newNode = NodeRegistration("testNodeForService", "address")
      val txTask = TxTask(
        service = Some(
          ServiceTask(
            verb = ServiceOp.Set,
            node = "testNodeForService",
            service = NewCatalogService(
              Service = "testService"
            )
          )
        )
      )

      runEither {
        for {
          _ <- client.registerEntity(newNode).body
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.results.flatMap(_.headOption).flatMap(_.service).exists(_.Service == "testService"))
          assert(result.errors.isEmpty)
        }
      }
    }

    "create Check" in withContainers { consul =>
      val client = createClient(consul)
      val newNode = NodeRegistration("testCheckNode", "address")
      val txTask = TxTask(
        check = Some(
          CheckTask(
            verb = CheckOp.Set,
            check = NewHealthCheck(
              node = "testCheckNode",
              name = "checkName",
              definition = Some(
                NewHealthCheckDefinition(
                  tcp = Some("localhost:8888")
                )
              )
            )
          )
        )
      )

      runEither {
        for {
          _ <- client.registerEntity(newNode).body
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.results.flatMap(_.headOption).flatMap(_.check).exists(_.checkId == "checkName"))
          assert(result.errors.isEmpty)
        }
      }
    }
  }
}
