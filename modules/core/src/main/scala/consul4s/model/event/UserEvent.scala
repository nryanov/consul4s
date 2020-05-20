package consul4s.model.event

import java.nio.charset.StandardCharsets
import java.util.Base64

final case class UserEvent(
  ID: String,
  Name: String,
  Payload: Option[String],
  NodeFilter: String,
  ServiceFilter: String,
  TagFilter: String,
  Version: Int,
  LTime: Long
) {
  lazy val decodedPayload: Option[String] =
    Payload.map(v => new String(Base64.getDecoder.decode(v.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
}
