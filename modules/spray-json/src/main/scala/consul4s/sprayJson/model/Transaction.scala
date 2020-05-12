package consul4s.sprayJson.model

import consul4s.model.transaction._
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Transaction extends DefaultJsonProtocol { this: Health with KV with Catalog with Agent =>
  implicit object CheckOpFormat extends RootJsonFormat[CheckOp] {
    override def write(obj: CheckOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): CheckOp = json match {
      case JsString(value) => CheckOp.withValue(value)
      case _               => deserializationError("CheckOp expected")
    }
  }

  implicit object KVOpFormat extends RootJsonFormat[KVOp] {
    override def write(obj: KVOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): KVOp = json match {
      case JsString(value) => KVOp.withValue(value)
      case _               => deserializationError("KVOp expected")
    }
  }

  implicit object NodeOpFormat extends RootJsonFormat[NodeOp] {
    override def write(obj: NodeOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): NodeOp = json match {
      case JsString(value) => NodeOp.withValue(value)
      case _               => deserializationError("NodeOp expected")
    }
  }

  implicit object SessionOpFormat extends RootJsonFormat[SessionOp] {
    override def write(obj: SessionOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): SessionOp = json match {
      case JsString(value) => SessionOp.withValue(value)
      case _               => deserializationError("SessionOp expected")
    }
  }

  implicit object ServiceOpFormat extends RootJsonFormat[ServiceOp] {
    override def write(obj: ServiceOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): ServiceOp = json match {
      case JsString(value) => ServiceOp.withValue(value)
      case _               => deserializationError("ServiceOp expected")
    }
  }

  implicit val checkTxnOpFormat: RootJsonFormat[CheckTxnOp] = jsonFormat(CheckTxnOp.apply, "Verb", "Check")

  implicit val kvTxnOpFormat: RootJsonFormat[KVTxnOp] =
    jsonFormat(KVTxnOp.apply, "Verb", "Key", "Value", "Flags", "Index", "Session", "Namespace")

  implicit val kvTxnResponseFormat: RootJsonFormat[KVTxnResponse] = jsonFormat(
    KVTxnResponse.apply,
    "Results",
    "Errors"
  )

  implicit val nodeTxnOpFormat: RootJsonFormat[NodeTxnOp] = jsonFormat(
    NodeTxnOp.apply,
    "Verb",
    "Check"
  )

  implicit val serviceTxnOpFormat: RootJsonFormat[ServiceTxnOp] = jsonFormat(
    ServiceTxnOp.apply,
    "Verb",
    "Node",
    "Service"
  )

  implicit val sessionTxnOpFormat: RootJsonFormat[SessionTxnOp] = jsonFormat(SessionTxnOp, "Verb")

  implicit val txnErrorFormat: RootJsonFormat[TxnError] = jsonFormat(TxnError.apply, "OpIndex", "What")

  implicit val txnOpFormat: RootJsonFormat[TxnOp] = jsonFormat(TxnOp.apply, "KV", "Node", "Service", "Check")

  implicit val txnResponseFormat: RootJsonFormat[TxnResponse] = jsonFormat(TxnResponse.apply, "Results", "Errors")

  implicit val txnResultFormat: RootJsonFormat[TxnResult] = jsonFormat(TxnResult.apply, "KV", "Node", "Service", "Check")
}
