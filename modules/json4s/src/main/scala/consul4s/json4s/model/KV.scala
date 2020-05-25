package consul4s.json4s.model

import consul4s.model.kv._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait KV {
  val kvPairFormat = FieldSerializer[KVPair](
    Map(),
    renameFrom("Key", "key")
      .orElse(renameFrom("CreateIndex", "createIndex"))
      .orElse(renameFrom("ModifyIndex", "modifyIndex"))
      .orElse(renameFrom("LockIndex", "lockIndex"))
      .orElse(renameFrom("Flags", "flags"))
      .orElse(renameFrom("Value", "value"))
      .orElse(renameFrom("Session", "session"))
  )

  val kvAllFieldSerializers = List(kvPairFormat)
}
