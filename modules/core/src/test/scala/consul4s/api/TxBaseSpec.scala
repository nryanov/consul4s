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
        KV = Some(
          KVTask(
            Verb = KVOp.Set,
            Key = "key",
            Value = Some(value)
          )
        )
      )

      runEither {
        for {
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.Errors.isEmpty)
          assert(result.Results.flatMap(_.headOption).flatMap(_.KV).exists(_.Key == "key"))
        }
      }
    }

    "create Node" in withContainers { consul =>
      val client = createClient(consul)
      val txTask = TxTask(
        Node = Some(
          NodeTask(
            Verb = NodeOp.Set,
            Node = NodeDefinition(
              Node = "testNode",
              Address = "localhost"
            )
          )
        )
      )

      runEither {
        for {
          result <- client.executeTx(List(txTask)).body
        } yield {
          assert(result.Results.flatMap(_.headOption).flatMap(_.Node).exists(_.Node == "testNode"))
          assert(result.Errors.isEmpty)
        }
      }
    }

    "create Service" in withContainers { consul =>
      val client = createClient(consul)
      val newNode = NodeRegistration("testNodeForService", "address")
      val txTask = TxTask(
        Service = Some(
          ServiceTask(
            Verb = ServiceOp.Set,
            Node = "testNodeForService",
            Service = NewCatalogService(
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
          assert(result.Results.flatMap(_.headOption).flatMap(_.Service).exists(_.Service == "testService"))
          assert(result.Errors.isEmpty)
        }
      }
    }

    "create Check" in withContainers { consul =>
      val client = createClient(consul)
      val newNode = NodeRegistration("testCheckNode", "address")
      val txTask = TxTask(
        Check = Some(
          CheckTask(
            Verb = CheckOp.Set,
            Check = NewHealthCheck(
              Node = "testCheckNode",
              Name = "checkName",
              Definition = Some(
                NewHealthCheckDefinition(
                  TCP = Some("localhost:8888")
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
          assert(result.Results.flatMap(_.headOption).flatMap(_.Check).exists(_.CheckID == "checkName"))
          assert(result.Errors.isEmpty)
        }
      }
    }
  }
}
