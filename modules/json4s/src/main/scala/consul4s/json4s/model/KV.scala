package consul4s.json4s.model

import consul4s.model.kv._
import org.json4s.FieldSerializer

trait KV {
  val kvPairFormat: FieldSerializer[KVPair] = FieldSerializer[KVPair]()

  val kvAllFieldSerializers = List(kvPairFormat)
}
