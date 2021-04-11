package consul4s.zio.json.model

import consul4s.model.session._
import consul4s.model.SessionBehavior
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Session { this: Common =>
  private[zio] case class SessionInfoRepr(
    ID: String,
    Name: String,
    Node: String,
    LockDelay: Long,
    CreateIndex: Long,
    ModifyIndex: Long,
    ServiceChecks: Option[List[String]],
    NodeChecks: Option[List[String]],
    Behavior: SessionBehavior,
    TTL: String
  )

  private[zio] case class SessionIdRepr(ID: String)

  private[zio] case class NewSessionRepr(
    Node: String,
    LockDelay: String,
    Name: Option[String] = None,
    ID: Option[String] = None,
    Checks: Option[List[String]] = Some(List("serfHealth")),
    Behavior: SessionBehavior = SessionBehavior.Release,
    TTL: Option[String] = None
  )

  implicit val sessionInfoCodec: JsonCodec[SessionInfo] = ConverterMacros.derive[SessionInfoRepr, SessionInfo]
  implicit val newSessionInfoCodec: JsonCodec[NewSession] = ConverterMacros.derive[NewSessionRepr, NewSession]
  implicit val sessionIdCodec: JsonCodec[SessionId] = ConverterMacros.derive[SessionIdRepr, SessionId]
}
