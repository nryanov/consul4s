package consul4s.circe.model

import consul4s.model.session._
import io.circe._
import io.circe.generic.semiauto._

trait Session { this: Common =>
  implicit val sessionInfoDecoder: Decoder[SessionInfo] = deriveDecoder[SessionInfo]
  implicit val sessionIdDecoder: Decoder[SessionId] = deriveDecoder[SessionId]

  implicit val sessionInfoEncoder: Encoder[SessionInfo] = deriveEncoder[SessionInfo]
  implicit val sessionIdEncoder: Encoder[SessionId] = deriveEncoder[SessionId]
}
