package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForEach
import consul4s.model.kv.KVPair
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

abstract class KVStoreBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForEach {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "kv store" should {
    "put and get key" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          create <- client.createOrUpdate("key", "value").body
          getRaw <- client.getRaw("key").body
          get <- client.get("key").body
        } yield {
          assert(create)
          assert(getRaw.contains("value"))
          assert(get.isDefined)
          assertResult("value")(get.get.decodedValue)
        }
      }
    }

    "put and get keys" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          _ <- client.createOrUpdate("key1", "value1").body
          _ <- client.createOrUpdate("key2", "value2").body
          getKeys <- client.getKeys("key").body
        } yield {
          assert(getKeys.contains(List("key1", "key2")))
        }
      }
    }

    "put and get multiple values" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          _ <- client.createOrUpdate("key1", "value1").body
          _ <- client.createOrUpdate("key2", "value2").body
          getRecurse <- client.getRecurse("key").body
        } yield {
          assert(getRecurse.map(_.map(_.decodedValue)).contains(List("value1", "value2")))
        }
      }
    }

    "get not existing key" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          getRaw <- client.getRaw("notExistingKey").body
          get <- client.get("notExistingKey").body
        } yield {
          assert(get.isEmpty)
          assert(getRaw.isEmpty)
        }
      }
    }

    "put and delete key" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          create <- client.createOrUpdate("forDeleting", "value").body
          delete <- client.delete("forDeleting").body
          get <- client.get("forDeleting").body
        } yield {
          assert(create)
          assert(delete)
          assert(get.isEmpty)
        }
      }
    }

    "put and delete recurse" in withContainers { consul =>
      val client = createClient(consul)

      runEither {
        for {
          _ <- client.createOrUpdate("key1", "value1").body
          _ <- client.createOrUpdate("key2", "value2").body
          _ <- client.createOrUpdate("key3", "value3").body
          getBeforeDelete <- client.getRecurse("key").body
          _ <- client.deleteRecurse("key").body
          getAfterDelete <- client.get("key").body
        } yield {
          assert(getBeforeDelete.map(_.map(_.decodedValue)).contains(List("value1", "value2", "value3")))
          assert(getAfterDelete.isEmpty)
        }
      }
    }
  }
}
