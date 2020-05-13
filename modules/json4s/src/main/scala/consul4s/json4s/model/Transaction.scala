package consul4s.json4s.model

import consul4s.model.agent.AgentService
import consul4s.model.catalog.{CatalogService, Node}
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction.{TxnError, _}
import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, JObject}

trait Transaction {
  val checkOpSerializer = new CustomSerializer[CheckOp](
    implicit format =>
      (
        {
          case JString(value) => CheckOp.withValue(value)
        }, {
          case v: CheckOp => JString(v.value)
        }
      )
  )

  val checkTxnOpSerializer = new CustomSerializer[CheckTxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            CheckTxnOp(
              (json \ "Verb").extract[CheckOp],
              (json \ "Check").extract[HealthCheck]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val kvOpSerializer = new CustomSerializer[KVOp](
    implicit format =>
      (
        {
          case JString(value) => KVOp.withValue(value)
        }, {
          case v: KVOp => JString(v.value)
        }
      )
  )

  val kvTxnOpSerializer = new CustomSerializer[KVTxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            KVTxnOp(
              (json \ "Verb").extract[KVOp],
              (json \ "Key").extract[String],
              (json \ "Value").extract[Array[Byte]],
              (json \ "Flags").extract[Long],
              (json \ "Index").extract[Long],
              (json \ "Session").extract[String],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val kvTxnResponseSerializer = new CustomSerializer[KVTxnResponse](
    implicit format =>
      (
        {
          case json: JObject =>
            KVTxnResponse(
              (json \ "Results").extract[List[KVPair]],
              (json \ "Errors").extract[List[TxnError]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val nodeOpSerializer = new CustomSerializer[NodeOp](
    implicit format =>
      (
        {
          case JString(value) => NodeOp.withValue(value)
        }, {
          case v: NodeOp => JString(v.value)
        }
      )
  )

  val nodeTxnOpSerializer = new CustomSerializer[NodeTxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            NodeTxnOp(
              (json \ "Verb").extract[NodeOp],
              (json \ "Check").extract[Node]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val serviceOpSerializer = new CustomSerializer[ServiceOp](
    implicit format =>
      (
        {
          case JString(value) => ServiceOp.withValue(value)
        }, {
          case v: ServiceOp => JString(v.value)
        }
      )
  )

  val serviceTxnOpSerializer = new CustomSerializer[ServiceTxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            ServiceTxnOp(
              (json \ "Verb").extract[ServiceOp],
              (json \ "Node").extract[String],
              (json \ "Service").extract[AgentService]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val sessionOpSerializer = new CustomSerializer[SessionOp](
    implicit format =>
      (
        {
          case JString(value) => SessionOp.withValue(value)
        }, {
          case v: SessionOp => JString(v.value)
        }
      )
  )

  val sessionTxnOpSerializer = new CustomSerializer[SessionTxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            SessionTxnOp(
              (json \ "Verb").extract[SessionOp]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val txnErrorSerializer = new CustomSerializer[TxnError](
    implicit format =>
      (
        {
          case json: JObject =>
            TxnError(
              (json \ "OpIndex").extract[Int],
              (json \ "What").extract[String]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val txnOpSerializer = new CustomSerializer[TxnOp](
    implicit format =>
      (
        {
          case json: JObject =>
            TxnOp(
              (json \ "KV").extract[KVPair],
              (json \ "Node").extract[NodeTxnOp],
              (json \ "Service").extract[ServiceTxnOp],
              (json \ "Check").extract[CheckTxnOp]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val txnResponseSerializer = new CustomSerializer[TxnResponse](
    implicit format =>
      (
        {
          case json: JObject =>
            TxnResponse(
              (json \ "Results").extract[List[TxnResult]],
              (json \ "Errors").extract[List[TxnError]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val txnResultSerializer = new CustomSerializer[TxnResult](
    implicit format =>
      (
        {
          case json: JObject =>
            TxnResult(
              (json \ "KV").extract[KVPair],
              (json \ "Node").extract[Node],
              (json \ "Service").extract[CatalogService],
              (json \ "Check").extract[HealthCheck]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val transactionAllSerializers = List(
    checkOpSerializer,
    checkTxnOpSerializer,
    kvOpSerializer,
    kvTxnOpSerializer,
    kvTxnResponseSerializer,
    nodeOpSerializer,
    nodeTxnOpSerializer,
    serviceOpSerializer,
    serviceTxnOpSerializer,
    sessionOpSerializer,
    sessionTxnOpSerializer,
    txnErrorSerializer,
    txnOpSerializer,
    txnResponseSerializer,
    txnResultSerializer
  )
}
