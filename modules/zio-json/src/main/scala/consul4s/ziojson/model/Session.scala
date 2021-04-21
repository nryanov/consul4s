package consul4s.ziojson.model

import consul4s.model.session._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Session { this: Common =>
  implicit val sessionInfoEncoder: JsonEncoder[SessionInfo] = DeriveJsonEncoder.gen[SessionInfo]
  implicit val newSessionInfoEncoder: JsonEncoder[NewSession] = DeriveJsonEncoder.gen[NewSession]
  implicit val sessionIdEncoder: JsonEncoder[SessionId] = DeriveJsonEncoder.gen[SessionId]

  implicit val sessionInfoCodec: JsonDecoder[SessionInfo] = DeriveJsonDecoder.gen[SessionInfo]
  implicit val newSessionInfoCodec: JsonDecoder[NewSession] = DeriveJsonDecoder.gen[NewSession]
  implicit val sessionIdCodec: JsonDecoder[SessionId] = DeriveJsonDecoder.gen[SessionId]
}
