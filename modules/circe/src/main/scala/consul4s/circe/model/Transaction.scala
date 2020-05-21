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
      ("Verb", a.verb.asJson),
      ("Key", a.key.asJson),
      ("Value", a.value.asJson),
      ("Flags", a.flags.asJson),
      ("Index", a.index.asJson),
      ("Session", a.session.asJson)
    )
  }

  implicit val checkTxEncoder: Encoder[CheckTask] = new Encoder[CheckTask] {
    override def apply(a: CheckTask): Json = Json.obj(
      ("Verb", a.verb.asJson),
      ("Check", a.check.asJson)
    )
  }

  implicit val newNodeEncoder: Encoder[NodeDefinition] = new Encoder[NodeDefinition] {
    override def apply(a: NodeDefinition): Json = Json.obj(
      ("Node", a.node.asJson),
      ("Address", a.address.asJson),
      ("ID", a.id.asJson),
      ("Datacenter", a.datacenter.asJson),
      ("TaggedAddresses", a.taggedAddresses.asJson),
      ("NodeMeta", a.nodeMeta.asJson)
    )
  }

  implicit val nodeTxEncoder: Encoder[NodeTask] = new Encoder[NodeTask] {
    override def apply(a: NodeTask): Json = Json.obj(
      ("Verb", a.verb.asJson),
      ("Node", a.node.asJson)
    )
  }

  implicit val serviceTxEncoder: Encoder[ServiceTask] = new Encoder[ServiceTask] {
    override def apply(a: ServiceTask): Json = Json.obj(
      ("Verb", a.verb.asJson),
      ("Node", a.node.asJson),
      ("Service", a.service.asJson)
    )
  }

  implicit val txTaskEncoder: Encoder[TxTask] = new Encoder[TxTask] {
    override def apply(a: TxTask): Json = Json.obj(
      ("KV", a.kv.asJson),
      ("Node", a.node.asJson),
      ("Service", a.service.asJson),
      ("Check", a.check.asJson)
    )
  }
}
