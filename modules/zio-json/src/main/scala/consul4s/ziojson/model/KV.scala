package consul4s.ziojson.model

import consul4s.model.kv.KVPair
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait KV {
  implicit val kvPairEncoder: JsonEncoder[KVPair] = DeriveJsonEncoder.gen[KVPair]

  implicit val kvPairDecoder: JsonDecoder[KVPair] = DeriveJsonDecoder.gen[KVPair]
}
