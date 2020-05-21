package consul4s.model.event

import java.nio.charset.StandardCharsets
import java.util.Base64

final case class UserEvent(
  id: String,
  name: String,
  payload: Option[String],
  nodeFilter: String,
  serviceFilter: String,
  tagFilter: String,
  version: Long,
  lTime: Long
) {
  lazy val decodedPayload: Option[String] =
    payload.map(v => new String(Base64.getDecoder.decode(v.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
}
