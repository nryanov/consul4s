package consul4s.ziojson.model

import consul4s.model.kv.KVPair
import zio.json.{DeriveJsonCodec, JsonCodec}

trait KV {
  implicit val kvPairCodec: JsonCodec[KVPair] = DeriveJsonCodec.gen[KVPair]
}
