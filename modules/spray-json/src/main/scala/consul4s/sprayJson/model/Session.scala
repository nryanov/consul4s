package consul4s.sprayJson.model

import consul4s.model.session._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Session extends DefaultJsonProtocol { this: Common =>
  implicit val newSessionFormat: RootJsonFormat[NewSession] =
    jsonFormat(NewSession.apply, "Node", "LockDelay", "Name", "ID", "Checks", "Behavior", "TTL")

  implicit val sessionIdFormat: RootJsonFormat[SessionId] = jsonFormat(SessionId, "ID")

  implicit val sessionInfoFormat: RootJsonFormat[SessionInfo] = jsonFormat(
    SessionInfo.apply,
    "ID",
    "Name",
    "Node",
    "LockDelay",
    "CreateIndex",
    "ModifyIndex",
    "ServiceChecks",
    "NodeChecks",
    "Behavior",
    "TTL"
  )

}
