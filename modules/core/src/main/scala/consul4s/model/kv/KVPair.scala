package consul4s.model.kv

import java.nio.charset.StandardCharsets
import java.util.Base64

final case class KVPair(
  Key: String,
  CreateIndex: Long,
  ModifyIndex: Long,
  LockIndex: Long,
  Flags: Long,
  Value: String,
  Session: Option[String]
) {
  lazy val decodedValue: String = new String(Base64.getDecoder.decode(Value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
}
