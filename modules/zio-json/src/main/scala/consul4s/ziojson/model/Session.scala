package consul4s.ziojson.model

import consul4s.model.session._
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Session { this: Common =>
  implicit val sessionInfoCodec: JsonCodec[SessionInfo] = DeriveJsonCodec.gen[SessionInfo]
  implicit val newSessionInfoCodec: JsonCodec[NewSession] = DeriveJsonCodec.gen[NewSession]
  implicit val sessionIdCodec: JsonCodec[SessionId] = DeriveJsonCodec.gen[SessionId]
}
