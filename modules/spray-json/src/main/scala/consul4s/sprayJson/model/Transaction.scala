package consul4s.sprayJson.model

import consul4s.model.transaction._
import consul4s.model.transaction.TxResults._
import consul4s.model.transaction.TxTask._
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Transaction extends DefaultJsonProtocol { this: Health with KV with Catalog with Agent =>
  implicit object KVOpFormat extends RootJsonFormat[KVOp] {
    override def write(obj: KVOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): KVOp = json match {
      case JsString(value) => KVOp.withValue(value)
      case _               => deserializationError("KVOp expected")
    }
  }

  implicit object CheckOpFormat extends RootJsonFormat[CheckOp] {
    override def write(obj: CheckOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): CheckOp = json match {
      case JsString(value) => CheckOp.withValue(value)
      case _               => deserializationError("CheckOp expected")
    }
  }

  implicit object NodeOpFormat extends RootJsonFormat[NodeOp] {
    override def write(obj: NodeOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): NodeOp = json match {
      case JsString(value) => NodeOp.withValue(value)
      case _               => deserializationError("NodeOp expected")
    }
  }

  implicit object ServiceOpFormat extends RootJsonFormat[ServiceOp] {
    override def write(obj: ServiceOp): JsValue = JsString(obj.value)

    override def read(json: JsValue): ServiceOp = json match {
      case JsString(value) => ServiceOp.withValue(value)
      case _               => deserializationError("ServiceOp expected")
    }
  }

  implicit val txResultFormat: RootJsonFormat[TxResult] = jsonFormat4(TxResult.apply)

  implicit val txErrorFormat: RootJsonFormat[TxError] = jsonFormat2(TxError.apply)

  implicit val txResultsFormat: RootJsonFormat[TxResults] = jsonFormat2(TxResults.apply)

  implicit val kvTaskFormat: RootJsonFormat[KVTask] = jsonFormat6(KVTask.apply)

  implicit val serviceTaskFormat: RootJsonFormat[ServiceTask] = jsonFormat3(ServiceTask.apply)

  implicit val nodeTaskFormat: RootJsonFormat[NodeTask] = rootFormat(lazyFormat(jsonFormat2(NodeTask.apply)))

  implicit val nodeDefinitionFormat: RootJsonFormat[NodeDefinition] = jsonFormat6(NodeDefinition.apply)

  implicit val checkTaskFormat: RootJsonFormat[CheckTask] = jsonFormat2(CheckTask.apply)

  implicit val txTaskFormat: RootJsonFormat[TxTask] = jsonFormat4(TxTask.apply)
}
