package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForEach
import consul4s.model.deprecated.KeyValue
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder}

abstract class KVStoreBaseSpec(implicit jsonDecoder: JsonDecoder) extends ConsulSpec with TestContainerForEach {
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

    "put and get keys" in withContainers { consul =>
      val client = createClient(consul)

      client.createOrUpdate("key1", "value1").body
      client.createOrUpdate("key2", "value2").body
      val getKeys: Option[List[String]] = client.getKeys("key").body

      assert(getKeys.contains(List("key1", "key2")))
    }

    "put and get multiple values" in withContainers { consul =>
      val client = createClient(consul)

      client.createOrUpdate("key1", "value1").body
      client.createOrUpdate("key2", "value2").body
      val getRecurse: Option[List[KeyValue]] = client.getRecurse("key").body

      assert(getRecurse.map(_.map(_.value)).contains(List("value1", "value2")))
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

    "put and delete recurse" in withContainers { consul =>
      val client = createClient(consul)

      client.createOrUpdate("key1", "value1").body
      client.createOrUpdate("key2", "value2").body
      client.createOrUpdate("key3", "value3").body
      val getBeforeDelete = client.getRecurse("key").body

      client.deleteRecurse("key").body
      val getAfterDelete = client.get("key").body

      assert(getBeforeDelete.map(_.map(_.value)).contains(List("value1", "value2", "value3")))
      assert(getAfterDelete.isEmpty)
    }
  }
}
