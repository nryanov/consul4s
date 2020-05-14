package consul4s.model.event

import java.nio.charset.StandardCharsets
import java.util.Base64

final case class UserEvent(
  ID: String,
  Name: String,
  Payload: String,
  NodeFilter: String,
  ServiceFilter: String,
  TagFilter: String,
  Version: Int,
  LTime: Int
) {
  lazy val decodedPayload: String = new String(Base64.getDecoder.decode(Payload.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
}
