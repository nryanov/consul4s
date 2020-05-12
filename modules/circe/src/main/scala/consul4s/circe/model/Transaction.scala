package consul4s.circe.model

import consul4s.model.agent.AgentService
import consul4s.model.catalog.{CatalogService, Node}
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction._
import io.circe.Decoder.Result
import io.circe._

trait Transaction { this: Health with KV with Catalog with Agent =>
  implicit val checkOpDecoder: Decoder[CheckOp] = new Decoder[CheckOp] {
    override def apply(c: HCursor): Result[CheckOp] = for {
      value <- c.as[String]
    } yield CheckOp.withValue(value)
  }

  implicit val checkTxnOpDecoder: Decoder[CheckTxnOp] = new Decoder[CheckTxnOp] {
    override def apply(c: HCursor): Result[CheckTxnOp] = for {
      verb <- c.downField("Verb").as[CheckOp]
      check <- c.downField("Check").as[HealthCheck]
    } yield CheckTxnOp(verb, check)
  }

  implicit val kvOpDecoder: Decoder[KVOp] = new Decoder[KVOp] {
    override def apply(c: HCursor): Result[KVOp] = for {
      value <- c.as[String]
    } yield KVOp.withValue(value)
  }

  implicit val kvTxnOpDecoder: Decoder[KVTxnOp] = new Decoder[KVTxnOp] {
    override def apply(c: HCursor): Result[KVTxnOp] = for {
      verb <- c.downField("Verb").as[KVOp]
      key <- c.downField("Key").as[String]
      value <- c.downField("Value").as[Array[Byte]]
      flags <- c.downField("Flags").as[Long]
      index <- c.downField("Index").as[Long]
      session <- c.downField("Session").as[String]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield KVTxnOp(
      verb,
      key,
      value,
      flags,
      index,
      session,
      namespace
    )
  }

  implicit val kvTxnResponseDecoder: Decoder[KVTxnResponse] = new Decoder[KVTxnResponse] {
    override def apply(c: HCursor): Result[KVTxnResponse] = for {
      results <- c.downField("Results").as[List[KVPair]]
      errors <- c.downField("Errors").as[List[TxnError]]
    } yield KVTxnResponse(results, errors)
  }

  implicit val nodeOpDecoder: Decoder[NodeOp] = new Decoder[NodeOp] {
    override def apply(c: HCursor): Result[NodeOp] = for {
      value <- c.as[String]
    } yield NodeOp.withValue(value)
  }

  implicit val nodeTxnOpDecoder: Decoder[NodeTxnOp] = new Decoder[NodeTxnOp] {
    override def apply(c: HCursor): Result[NodeTxnOp] = for {
      verb <- c.downField("Verb").as[NodeOp]
      check <- c.downField("Check").as[Node]
    } yield NodeTxnOp(verb, check)
  }

  implicit val serviceOpDecoder: Decoder[ServiceOp] = new Decoder[ServiceOp] {
    override def apply(c: HCursor): Result[ServiceOp] = for {
      value <- c.as[String]
    } yield ServiceOp.withValue(value)
  }

  implicit val serviceTxnOpDecoder: Decoder[ServiceTxnOp] = new Decoder[ServiceTxnOp] {
    override def apply(c: HCursor): Result[ServiceTxnOp] = for {
      verb <- c.downField("Verb").as[ServiceOp]
      node <- c.downField("Node").as[String]
      service <- c.downField("Service").as[AgentService]
    } yield ServiceTxnOp(verb, node, service)
  }

  implicit val sessionOpDecoder: Decoder[SessionOp] = new Decoder[SessionOp] {
    override def apply(c: HCursor): Result[SessionOp] = for {
      value <- c.as[String]
    } yield SessionOp.withValue(value)
  }

  implicit val sessionTxnOpDecoder: Decoder[SessionTxnOp] = new Decoder[SessionTxnOp] {
    override def apply(c: HCursor): Result[SessionTxnOp] = for {
      verb <- c.downField("Verb").as[SessionOp]
    } yield SessionTxnOp(verb)
  }

  implicit val txnErrorDecoder: Decoder[TxnError] = new Decoder[TxnError] {
    override def apply(c: HCursor): Result[TxnError] = for {
      opIndex <- c.downField("OpIndex").as[Int]
      what <- c.downField("What").as[String]
    } yield TxnError(opIndex, what)
  }

  implicit val txnOpDecoder: Decoder[TxnOp] = new Decoder[TxnOp] {
    override def apply(c: HCursor): Result[TxnOp] = for {
      kv <- c.downField("KV").as[KVPair]
      node <- c.downField("Node").as[NodeTxnOp]
      service <- c.downField("Service").as[ServiceTxnOp]
      check <- c.downField("Check").as[CheckTxnOp]
    } yield TxnOp(kv, node, service, check)
  }

  implicit val txnResponseDecoder: Decoder[TxnResponse] = new Decoder[TxnResponse] {
    override def apply(c: HCursor): Result[TxnResponse] = for {
      results <- c.downField("Results").as[List[TxnResult]]
      errors <- c.downField("Errors").as[List[TxnError]]
    } yield TxnResponse(results, errors)
  }

  implicit val txnResultDecoder: Decoder[TxnResult] = new Decoder[TxnResult] {
    override def apply(c: HCursor): Result[TxnResult] = for {
      kv <- c.downField("KV").as[KVPair]
      node <- c.downField("Node").as[Node]
      service <- c.downField("Service").as[CatalogService]
      check <- c.downField("Check").as[HealthCheck]
    } yield TxnResult(kv, node, service, check)
  }

}
