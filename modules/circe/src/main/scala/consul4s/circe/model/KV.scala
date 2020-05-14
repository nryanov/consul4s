package consul4s.circe.model

import consul4s.model.kv.KVPair
import io.circe._
import io.circe.generic.semiauto._

trait KV {
  implicit val kvPairEncoder: Encoder[KVPair] = deriveEncoder[KVPair]
  implicit val kvPairDecoder: Decoder[KVPair] = deriveDecoder[KVPair]
}
