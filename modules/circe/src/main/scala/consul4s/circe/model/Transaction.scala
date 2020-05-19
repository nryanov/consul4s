package consul4s.circe.model

import consul4s.model.transaction._
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction.TxResults._
import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.semiauto._

trait Transaction { this: Common with Agent with Health with KV with Catalog =>
  implicit val kvOpDecoder: Decoder[KVOp] = new Decoder[KVOp] {
    override def apply(c: HCursor): Result[KVOp] = for {
      value <- c.as[String]
    } yield KVOp.withValue(value)
  }

  implicit val kvOpEncoder: Encoder[KVOp] = new Encoder[KVOp] {
    override def apply(a: KVOp): Json = Json.fromString(a.value)
  }

  implicit val checkOpDecoder: Decoder[CheckOp] = new Decoder[CheckOp] {
    override def apply(c: HCursor): Result[CheckOp] = for {
      value <- c.as[String]
    } yield CheckOp.withValue(value)
  }

  implicit val checkOpEncoder: Encoder[CheckOp] = new Encoder[CheckOp] {
    override def apply(a: CheckOp): Json = Json.fromString(a.value)
  }

  implicit val nodeOpDecoder: Decoder[NodeOp] = new Decoder[NodeOp] {
    override def apply(c: HCursor): Result[NodeOp] = for {
      value <- c.as[String]
    } yield NodeOp.withValue(value)
  }

  implicit val nodeOpEncoder: Encoder[NodeOp] = new Encoder[NodeOp] {
    override def apply(a: NodeOp): Json = Json.fromString(a.value)
  }

  implicit val serviceOpDecoder: Decoder[ServiceOp] = new Decoder[ServiceOp] {
    override def apply(c: HCursor): Result[ServiceOp] = for {
      value <- c.as[String]
    } yield ServiceOp.withValue(value)
  }

  implicit val serviceOpEncoder: Encoder[ServiceOp] = new Encoder[ServiceOp] {
    override def apply(a: ServiceOp): Json = Json.fromString(a.value)
  }

  implicit val kvTxResultDecoder: Decoder[KVPairTx] = deriveDecoder[KVPairTx]
  implicit val serviceTxResultDecoder: Decoder[Service] = deriveDecoder[Service]
  implicit val txErrorDecoder: Decoder[TxError] = deriveDecoder[TxError]
  implicit val txResultDecoder: Decoder[TxResult] = deriveDecoder[TxResult]
  implicit val txResultsDecoder: Decoder[TxResults] = deriveDecoder[TxResults]

  implicit val kvTxEncoder: Encoder[KVTask] = deriveEncoder[KVTask]
  implicit val checkDefinitionEncoder: Encoder[CheckDefinition] = deriveEncoder[CheckDefinition]
  implicit val checkTxEncoder: Encoder[CheckTask] = deriveEncoder[CheckTask]
  implicit val newNodeEncoder: Encoder[NodeDefinition] = deriveEncoder[NodeDefinition]
  implicit val nodeTxEncoder: Encoder[NodeTask] = deriveEncoder[NodeTask]
  implicit val serviceTxEncoder: Encoder[ServiceTask] = deriveEncoder[ServiceTask]
  implicit val txTaskEncoder: Encoder[TxTask] = deriveEncoder[TxTask]
}
