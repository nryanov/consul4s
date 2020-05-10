package consul4s.model

import java.nio.charset.StandardCharsets
import java.util.Base64

case class KeyValue(createIndex: Long, modifyIndex: Long, lockIndex: Long, key: String, valueBase64: String, flags: Int) {
  lazy val value: String = new String(Base64.getDecoder.decode(valueBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)
}
