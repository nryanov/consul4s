package consul4s.ziojson.model

import consul4s.model.transaction.TxResults._
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Transaction { this: Common with Agent with Health with KV with Catalog =>
  implicit val kvOpEncoder: JsonEncoder[KVOp] = JsonEncoder.string.contramap(_.value)
  implicit val checkOpEncoder: JsonEncoder[CheckOp] = JsonEncoder.string.contramap(_.value)
  implicit val nodeOpEncoder: JsonEncoder[NodeOp] = JsonEncoder.string.contramap(_.value)
  implicit val serviceOpEncoder: JsonEncoder[ServiceOp] = JsonEncoder.string.contramap(_.value)
  implicit val txErrorEncoder: JsonEncoder[TxError] = DeriveJsonEncoder.gen[TxError]
  implicit val txResultEncoder: JsonEncoder[TxResult] = DeriveJsonEncoder.gen[TxResult]
  implicit val txResultsEncoder: JsonEncoder[TxResults] = DeriveJsonEncoder.gen[TxResults]
  implicit val kvTaskEncoder: JsonEncoder[KVTask] = DeriveJsonEncoder.gen[KVTask]
  implicit val checkTaskEncoder: JsonEncoder[CheckTask] = DeriveJsonEncoder.gen[CheckTask]
  implicit val nodeDefinitionEncoder: JsonEncoder[NodeDefinition] = DeriveJsonEncoder.gen[NodeDefinition]
  implicit val nodeTaskEncoder: JsonEncoder[NodeTask] = DeriveJsonEncoder.gen[NodeTask]
  implicit val serviceTaskEncoder: JsonEncoder[ServiceTask] = DeriveJsonEncoder.gen[ServiceTask]
  implicit val txTaskEncoder: JsonEncoder[TxTask] = DeriveJsonEncoder.gen[TxTask]

  implicit val kvOpDecoder: JsonDecoder[KVOp] = JsonDecoder.string.map(v => KVOp.withValue(v))
  implicit val checkOpDecoder: JsonDecoder[CheckOp] = JsonDecoder.string.map(v => CheckOp.withValue(v))
  implicit val nodeOpDecoder: JsonDecoder[NodeOp] = JsonDecoder.string.map(v => NodeOp.withValue(v))
  implicit val serviceOpDecoder: JsonDecoder[ServiceOp] = JsonDecoder.string.map(v => ServiceOp.withValue(v))
  implicit val txErrorDecoder: JsonDecoder[TxError] = DeriveJsonDecoder.gen[TxError]
  implicit val txResultDecoder: JsonDecoder[TxResult] = DeriveJsonDecoder.gen[TxResult]
  implicit val txResultsDecoder: JsonDecoder[TxResults] = DeriveJsonDecoder.gen[TxResults]
  implicit val kvTaskDecoder: JsonDecoder[KVTask] = DeriveJsonDecoder.gen[KVTask]
  implicit val checkTaskDecoder: JsonDecoder[CheckTask] = DeriveJsonDecoder.gen[CheckTask]
  implicit val nodeDefinitionDecoder: JsonDecoder[NodeDefinition] = DeriveJsonDecoder.gen[NodeDefinition]
  implicit val nodeTaskDecoder: JsonDecoder[NodeTask] = DeriveJsonDecoder.gen[NodeTask]
  implicit val serviceTaskDecoder: JsonDecoder[ServiceTask] = DeriveJsonDecoder.gen[ServiceTask]
  implicit val txTaskDecoder: JsonDecoder[TxTask] = DeriveJsonDecoder.gen[TxTask]
}
