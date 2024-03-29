package consul4s.sprayJson.model

import consul4s.model.{CheckStatus, ServiceKind, SessionBehavior}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Common extends DefaultJsonProtocol {
  implicit object CheckStatusFormat extends RootJsonFormat[CheckStatus] {
    override def write(obj: CheckStatus): JsValue = JsString(obj.value)

    override def read(json: JsValue): CheckStatus = json match {
      case JsString(value) =>
        value match {
          case CheckStatus(r) => r
          case _              => deserializationError(s"Can't convert $value to CheckStatus")
        }
      case _ => deserializationError("CheckStatus expected")
    }
  }

  implicit object ServiceKindFormat extends RootJsonFormat[ServiceKind] {
    override def write(obj: ServiceKind): JsValue = JsString(obj.value)

    override def read(json: JsValue): ServiceKind = json match {
      case JsString(value) =>
        value match {
          case ServiceKind(r) => r
          case _              => deserializationError(s"Can't convert $value to ServiceKind")
        }
      case _ => deserializationError("ServiceKind expected")
    }
  }

  implicit object SessionBehaviorFormat extends RootJsonFormat[SessionBehavior] {
    override def write(obj: SessionBehavior): JsValue = JsString(obj.value)

    override def read(json: JsValue): SessionBehavior = json match {
      case JsString(value) =>
        value match {
          case SessionBehavior(r) => r
          case _                  => deserializationError(s"Can't convert $value to SessionBehavior")
        }
      case _ => deserializationError("SessionBehavior expected")
    }
  }

}
