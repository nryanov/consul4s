package consul4s.circe.model

import consul4s.model.SessionBehavior
import consul4s.model.session._
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait Session { this: Common =>
  implicit val sessionInfoDecoder: Decoder[SessionInfo] = new Decoder[SessionInfo] {
    override def apply(c: HCursor): Result[SessionInfo] = for {
      id <- c.downField("ID").as[String]
      name <- c.downField("Name").as[String]
      node <- c.downField("Node").as[String]
      lockDelay <- c.downField("LockDelay").as[Long]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
      serviceChecks <- c.downField("ServiceChecks").as[Option[List[String]]]
      nodeChecks <- c.downField("NodeChecks").as[Option[List[String]]]
      behavior <- c.downField("Behavior").as[SessionBehavior]
      ttl <- c.downField("TTL").as[String]
    } yield SessionInfo(
      id,
      name,
      node,
      lockDelay,
      createIndex,
      modifyIndex,
      serviceChecks,
      nodeChecks,
      behavior,
      ttl
    )
  }

  implicit val sessionIdDecoder: Decoder[SessionId] = new Decoder[SessionId] {
    override def apply(c: HCursor): Result[SessionId] = for {
      id <- c.downField("ID").as[String]
    } yield SessionId(id)
  }

  implicit val newSessionInfoEncoder: Encoder[NewSession] = new Encoder[NewSession] {
    override def apply(a: NewSession): Json = Json.obj(
      ("Node", a.node.asJson),
      ("LockDelay", a.lockDelay.asJson),
      ("Name", a.name.asJson),
      ("ID", a.id.asJson),
      ("Checks", a.checks.asJson),
      ("Behavior", a.behavior.asJson),
      ("TTL", a.ttl.asJson)
    )
  }

  implicit val sessionIdEncoder: Encoder[SessionId] = new Encoder[SessionId] {
    override def apply(a: SessionId): Json = Json.obj(
      ("ID", a.id.asJson)
    )
  }
}
