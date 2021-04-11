package consul4s.zio.json.model

import consul4s.model.kv.KVPair
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait KV {
  private[zio] case class KVPairRepr(
    Key: String,
    CreateIndex: Long,
    ModifyIndex: Long,
    LockIndex: Long,
    Flags: Long,
    Value: Option[String],
    Session: Option[String]
  )

  implicit val kvPairCodec: JsonCodec[KVPair] = ConverterMacros.derive[KVPairRepr, KVPair]
}
