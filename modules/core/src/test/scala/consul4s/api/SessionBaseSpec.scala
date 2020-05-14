package consul4s.api

import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import consul4s.model.catalog.{EntityDeregistration, EntityRegistration}
import consul4s.model.session.SessionInfo
import consul4s.{ConsulContainer, ConsulSpec, JsonDecoder, JsonEncoder}

// fixme: depends on check ability which allows to register checks
class SessionBaseSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends ConsulSpec with TestContainerForAll {
  override val containerDef: ConsulContainer.Def = ConsulContainer.Def()

  "session api" should {
    "create session" in withContainers { consul =>
      val client = createClient(consul)

      // node should exist
      client.registerEntity(EntityRegistration("node", "address"))
      val session = SessionInfo("node", "15s")

      val sessionId = client.createSession(session).body

      val sessionList = client.listSession().body

      assert(sessionList.exists(_.ID.contains(sessionId.ID)))

      client.deregisterEntity(EntityDeregistration("node"))
    }

    "create session and list sessions for specific node" in withContainers { consul =>
      val client = createClient(consul)

      // node should exist
      client.registerEntity(EntityRegistration("node", "address"))
      val session = SessionInfo("node", "15s")

      assert(client.listNodeSession("node").body.isEmpty)

      val sessionId = client.createSession(session).body

      val sessionList = client.listNodeSession("node").body

      assert(sessionList.exists(_.ID.contains(sessionId.ID)))

      client.deregisterEntity(EntityDeregistration("node"))
    }

    "create session and get session info" in withContainers { consul =>
      val client = createClient(consul)

      // node should exist
      client.registerEntity(EntityRegistration("node", "address"))
      val session = SessionInfo("node", "15s")

      val sessionId = client.createSession(session).body

      val sessionInfo = client.readSession(sessionId).body

      assert(sessionInfo.exists(_.ID.contains(sessionId.ID)))

      client.deregisterEntity(EntityDeregistration("node"))
    }

    "create session, get session info and delete" in withContainers { consul =>
      val client = createClient(consul)

      // node should exist
      client.registerEntity(EntityRegistration("node", "address"))
      val session = SessionInfo("node", "15s")

      val sessionId = client.createSession(session).body

      val sessionInfo = client.readSession(sessionId).body

      assert(sessionInfo.exists(_.ID.contains(sessionId.ID)))

      client.deleteSession(sessionId)

      val sessionInfoAfterDeletion = client.readSession(sessionId).body

      assert(sessionInfoAfterDeletion.isEmpty)

      client.deregisterEntity(EntityDeregistration("node"))
    }

    "create and renew session" in withContainers { consul =>
      val client = createClient(consul)

      // node should exist
      client.registerEntity(EntityRegistration("node", "address"))
      val session = SessionInfo("node", "15s")

      val sessionId = client.createSession(session).body

      val sessionList = client.listSession().body

      assert(sessionList.exists(_.ID.contains(sessionId.ID)))

      val response = client.renewSession(sessionId).body

      assert(response.exists(_.ID.contains(sessionId.ID)))

      client.deregisterEntity(EntityDeregistration("node"))
    }
  }
}
