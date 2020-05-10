package consul4s.api

import sttp.client._
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.{BaseSpec, ConsulClient, ConsulContainer, JsonDecoder}

abstract class KVStoreBaseSpec(implicit jsonDecoder: JsonDecoder) extends BaseSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "kv store" should {
    "put and get key" in withContainers { consul =>
      val client = createClient(consul)

      val create = client.createOrUpdate("key", "value").body
      val getRaw = client.getRaw("key").body
      val get = client.get("key").body

      assert(create)
      assert(getRaw.contains("value"))
      assert(get.isDefined)
      assertResult("value")(get.get.value)
    }

    "get not existing key" in withContainers { consul =>
      val client = createClient(consul)

      val getRaw = client.getRaw("notExistingKey").body
      val get = client.get("notExistingKey").body

      assert(get.isEmpty)
      assert(getRaw.isEmpty)
    }

    "put and delete key" in withContainers { consul =>
      val client = createClient(consul)

      val create = client.createOrUpdate("forDeleting", "value").body
      val delete = client.delete("forDeleting").body
      val get = client.get("forDeleting").body

      assert(create)
      assert(delete)
      assert(get.isEmpty)
    }
  }

  private def createClient(consul: ConsulContainer): KVStore[Identity] = {
    val backend = HttpURLConnectionBackend()
    val client = ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}/v1", backend)

    client.kvStore()
  }
}
