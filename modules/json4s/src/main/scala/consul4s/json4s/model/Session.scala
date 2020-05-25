package consul4s.json4s.model

import consul4s.model.session._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Session {
  val newSessionFormat = FieldSerializer[NewSession](
    renameTo("node", "Node")
      .orElse(renameTo("lockDelay", "LockDelay"))
      .orElse(renameTo("name", "Name"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("checks", "Checks"))
      .orElse(renameTo("behavior", "Behavior"))
      .orElse(renameTo("ttl", "TTL")),
    Map()
  )

  val sessionIdFormat = FieldSerializer[SessionId](Map(), renameFrom("ID", "id"))
  val sessionInfoFormat = FieldSerializer[SessionInfo](
    Map(),
    renameFrom("ID", "id")
      .orElse(renameFrom("Name", "name"))
      .orElse(renameFrom("Node", "node"))
      .orElse(renameFrom("LockDelay", "lockDelay"))
      .orElse(renameFrom("CreateIndex", "createIndex"))
      .orElse(renameFrom("ModifyIndex", "modifyIndex"))
      .orElse(renameFrom("ServiceChecks", "serviceChecks"))
      .orElse(renameFrom("NodeChecks", "nodeChecks"))
      .orElse(renameFrom("Behavior", "behavior"))
      .orElse(renameFrom("TTL", "ttl"))
  )

  val sessionFieldAllSerializers = List(newSessionFormat, sessionIdFormat, sessionInfoFormat)
}
