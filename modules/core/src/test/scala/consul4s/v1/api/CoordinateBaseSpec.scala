package consul4s.v1.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog.NodeRegistration
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

class CoordinateBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "coordinate" should {
    "return coordinate for datacenters" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          r <- client.getDatacenterCoordinates().body
        } yield assert(true)
      }
    }

    "return coordinate for nodes" in withContainers { consul =>
      val client = createClient(consul)
      val entityRegistration = NodeRegistration("myNode", "someAddress")

      runEither {
        for {
          _ <- client.registerEntity(entityRegistration).body
          r <- client.getNodeCoordinates(dc = Some("dc1")).body
        } yield assert(true)
      }
    }

    "return coordinate for single node" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          node <- client.getDatacenterNodes().body
          r <- client.getNodeCoordinate(node.head.node).body
        } yield assert(true)
      }
    }

    // todo: update coordinate for node
  }
}
