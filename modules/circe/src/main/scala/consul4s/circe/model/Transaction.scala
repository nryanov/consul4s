package consul4s.circe.model

import consul4s.model.agent.Service
import consul4s.model.catalog.Node
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction._
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction.TxResults._
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait Transaction { this: Common with Agent with Health with KV with Catalog =>
  implicit val kvOpDecoder: Decoder[KVOp] = new Decoder[KVOp] {
    override def apply(c: HCursor): Result[KVOp] =
      c.as[String].flatMap {
        case KVOp(value) => Right(value)
        case str         => Left(DecodingFailure(s"Can't convert $str to KVOp", c.history))
      }
  }

  implicit val kvOpEncoder: Encoder[KVOp] = new Encoder[KVOp] {
    override def apply(a: KVOp): Json = Json.fromString(a.value)
  }

  implicit val checkOpDecoder: Decoder[CheckOp] = new Decoder[CheckOp] {
    override def apply(c: HCursor): Result[CheckOp] =
      c.as[String].flatMap {
        case CheckOp(value) => Right(value)
        case str            => Left(DecodingFailure(s"Can't convert $str to CheckOp", c.history))
      }
  }

  implicit val checkOpEncoder: Encoder[CheckOp] = new Encoder[CheckOp] {
    override def apply(a: CheckOp): Json = Json.fromString(a.value)
  }

  implicit val nodeOpDecoder: Decoder[NodeOp] = new Decoder[NodeOp] {
    override def apply(c: HCursor): Result[NodeOp] =
      c.as[String].flatMap {
        case NodeOp(value) => Right(value)
        case str           => Left(DecodingFailure(s"Can't convert $str to NodeOp", c.history))
      }
  }

  implicit val nodeOpEncoder: Encoder[NodeOp] = new Encoder[NodeOp] {
    override def apply(a: NodeOp): Json = Json.fromString(a.value)
  }

  implicit val serviceOpDecoder: Decoder[ServiceOp] = new Decoder[ServiceOp] {
    override def apply(c: HCursor): Result[ServiceOp] =
      c.as[String].flatMap {
        case ServiceOp(value) => Right(value)
        case str              => Left(DecodingFailure(s"Can't convert $str to ServiceOp", c.history))
      }
  }

  implicit val serviceOpEncoder: Encoder[ServiceOp] = new Encoder[ServiceOp] {
    override def apply(a: ServiceOp): Json = Json.fromString(a.value)
  }

  implicit val txErrorDecoder: Decoder[TxError] = new Decoder[TxError] {
    override def apply(c: HCursor): Result[TxError] = for {
      opIndex <- c.downField("OpIndex").as[Long]
      what <- c.downField("What").as[String]
    } yield TxError(opIndex, what)
  }

  implicit val txResultDecoder: Decoder[TxResult] = new Decoder[TxResult] {
    override def apply(c: HCursor): Result[TxResult] = for {
      kv <- c.downField("KV").as[Option[KVPair]]
      node <- c.downField("Node").as[Option[Node]]
      service <- c.downField("Service").as[Option[Service]]
      check <- c.downField("Check").as[Option[HealthCheck]]
    } yield TxResult(kv, node, service, check)
  }

  implicit val txResultsDecoder: Decoder[TxResults] = new Decoder[TxResults] {
    override def apply(c: HCursor): Result[TxResults] = for {
      results <- c.downField("Results").as[Option[List[TxResult]]]
      errors <- c.downField("Errors").as[Option[List[TxError]]]
    } yield TxResults(results, errors)
  }

  implicit val kvTxEncoder: Encoder[KVTask] = new Encoder[KVTask] {
    override def apply(a: KVTask): Json = Json.obj(
      ("Verb", a.Verb.asJson),
      ("Key", a.Key.asJson),
      ("Value", a.Value.asJson),
      ("Flags", a.Flags.asJson),
      ("Index", a.Index.asJson),
      ("Session", a.Session.asJson)
    )
  }

  implicit val checkTxEncoder: Encoder[CheckTask] = new Encoder[CheckTask] {
    override def apply(a: CheckTask): Json = Json.obj(
      ("Verb", a.Verb.asJson),
      ("Check", a.Check.asJson)
    )
  }

  implicit val newNodeEncoder: Encoder[NodeDefinition] = new Encoder[NodeDefinition] {
    override def apply(a: NodeDefinition): Json = Json.obj(
      ("Node", a.Node.asJson),
      ("Address", a.Address.asJson),
      ("ID", a.ID.asJson),
      ("Datacenter", a.Datacenter.asJson),
      ("TaggedAddresses", a.TaggedAddresses.asJson),
      ("NodeMeta", a.NodeMeta.asJson)
    )
  }

  implicit val nodeTxEncoder: Encoder[NodeTask] = new Encoder[NodeTask] {
    override def apply(a: NodeTask): Json = Json.obj(
      ("Verb", a.Verb.asJson),
      ("Node", a.Node.asJson)
    )
  }

  implicit val serviceTxEncoder: Encoder[ServiceTask] = new Encoder[ServiceTask] {
    override def apply(a: ServiceTask): Json = Json.obj(
      ("Verb", a.Verb.asJson),
      ("Node", a.Node.asJson),
      ("Service", a.Service.asJson)
    )
  }

  implicit val txTaskEncoder: Encoder[TxTask] = new Encoder[TxTask] {
    override def apply(a: TxTask): Json = Json.obj(
      ("KV", a.KV.asJson),
      ("Node", a.Node.asJson),
      ("Service", a.Service.asJson),
      ("Check", a.Check.asJson)
    )
  }
}
