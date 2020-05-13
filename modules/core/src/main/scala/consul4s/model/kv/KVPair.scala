package consul4s.model.kv

import java.nio.charset.StandardCharsets
import java.util.Base64

final case class KVPair(
  key: String,
  createIndex: Long,
  modifyIndex: Long,
  lockIndex: Long,
  flags: Long,
  valueBase64: String,
  session: Option[String],
  namespace: Option[String]
) {
  lazy val value: String = new String(Base64.getDecoder.decode(valueBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
}
