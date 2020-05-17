package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog.EntityRegistration
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

class CoordinateBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "coordinate" should {
    "return coordinate for datacenters" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          r <- client.coordinateWANDatacenters().body
        } yield {
          assert(true)
        }
      }
    }

    "return coordinate for nodes" in withContainers { consul =>
      val client = createClient(consul)
      val entityRegistration = EntityRegistration("myNode", "someAddress")

      runEither {
        for {
          _ <- client.registerEntity(entityRegistration).body
          r <- client.coordinateLANNodes(dc = Some("dc1")).body
        } yield {
          assert(true)
        }
      }
    }

    "return coordinate for single node" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          node <- client.nodes().body
          r <- client.coordinateLANNode(node.head.Node).body
        } yield {
          assert(true)
        }
      }
    }

    // todo: update coordinate for node
  }
}
