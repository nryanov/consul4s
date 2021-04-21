package consul4s.ziojson.model

import consul4s.model.transaction.TxResults._
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction._
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Transaction { this: Common with Agent with Health with KV with Catalog =>
  implicit val kvOpCodec: JsonCodec[KVOp] = JsonCodec.string.xmap(v => KVOp.withValue(v), _.value)
  implicit val checkOpCodec: JsonCodec[CheckOp] = JsonCodec.string.xmap(v => CheckOp.withValue(v), _.value)
  implicit val nodeOpCodec: JsonCodec[NodeOp] = JsonCodec.string.xmap(v => NodeOp.withValue(v), _.value)
  implicit val serviceOpCodec: JsonCodec[ServiceOp] = JsonCodec.string.xmap(v => ServiceOp.withValue(v), _.value)

  implicit val txErrorCodec: JsonCodec[TxError] = DeriveJsonCodec.gen[TxError]
  implicit val txResultCodec: JsonCodec[TxResult] = DeriveJsonCodec.gen[TxResult]
  implicit val txResultsCodec: JsonCodec[TxResults] = DeriveJsonCodec.gen[TxResults]
  implicit val kvTaskCodec: JsonCodec[KVTask] = DeriveJsonCodec.gen[KVTask]
  implicit val checkTaskCodec: JsonCodec[CheckTask] = DeriveJsonCodec.gen[CheckTask]
  implicit val nodeDefinitionCodec: JsonCodec[NodeDefinition] = DeriveJsonCodec.gen[NodeDefinition]
  implicit val nodeTaskCodec: JsonCodec[NodeTask] = DeriveJsonCodec.gen[NodeTask]
  implicit val serviceTaskCodec: JsonCodec[ServiceTask] = DeriveJsonCodec.gen[ServiceTask]
  implicit val TxTaskCodec: JsonCodec[TxTask] = DeriveJsonCodec.gen[TxTask]
}
