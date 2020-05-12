package consul4s.sprayJson.model

import consul4s.model.{ServiceKind, Status}
import spray.json.{JsString, JsValue, RootJsonFormat, deserializationError}

trait Common {
  implicit object StatusFormat extends RootJsonFormat[Status] {
    override def write(obj: Status): JsValue = JsString(obj.value)

    override def read(json: JsValue): Status = json match {
      case JsString(value) => Status.withValue(value)
      case _               => deserializationError("Status expected")
    }
  }

  implicit object ServiceKindFormat extends RootJsonFormat[ServiceKind] {
    override def write(obj: ServiceKind): JsValue = JsString(obj.value)

    override def read(json: JsValue): ServiceKind = json match {
      case JsString(value) => ServiceKind.withValue(value)
      case _               => deserializationError("ServiceKind expected")
    }
  }
}
