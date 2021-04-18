package consul4s.json4s.model

import consul4s.model.session._
import org.json4s.FieldSerializer

trait Session {
  val newSessionFormat: FieldSerializer[NewSession] = FieldSerializer[NewSession]()
  val sessionIdFormat: FieldSerializer[SessionId] = FieldSerializer[SessionId]()
  val sessionInfoFormat: FieldSerializer[SessionInfo] = FieldSerializer[SessionInfo]()

  val sessionFieldAllSerializers = List(newSessionFormat, sessionIdFormat, sessionInfoFormat)
}
