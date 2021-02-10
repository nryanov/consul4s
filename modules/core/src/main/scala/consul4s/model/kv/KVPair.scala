package consul4s.model.kv

import java.nio.charset.StandardCharsets
import java.util.Base64

/**
 * @param key - is simply the full path of the entry.
 * @param createIndex - is the internal index value that represents when the entry was created.
 * @param modifyIndex - is the last index that modified this key.
 *                    This index corresponds to the X-Consul-Index header value that is returned in responses,
 *                    and it can be used to establish blocking queries by setting the ?index query parameter.
 *                    You can even perform blocking queries against entire subtrees of the KV store:
 *                    if ?recurse is provided, the returned X-Consul-Index corresponds
 *                    to the latest ModifyIndex within the prefix, and a blocking query using that ?index will wait
 *                    until any key within that prefix is updated.
 * @param lockIndex - is the number of times this key has successfully been acquired in a lock.
 *                  If the lock is held, the Session key provides the session that owns the lock.
 * @param flags - is an opaque unsigned integer that can be attached to each entry.
 *              Clients can choose to use this however makes sense for their application.
 * @param value - is a base64-encoded blob of data.
 * @param session - Specifies a session.
 */
final case class KVPair(
  key: String,
  createIndex: Long,
  modifyIndex: Long,
  lockIndex: Long,
  flags: Long,
  value: Option[String],
  session: Option[String]
) {
  lazy val decodedValue: Option[String] =
    value.map(v => new String(Base64.getDecoder.decode(v.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
}
