package consul4s.sprayJson.model

import consul4s.model.event.UserEvent
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Event extends DefaultJsonProtocol {
  implicit val userEventFormat: RootJsonFormat[UserEvent] =
    jsonFormat(UserEvent.apply, "ID", "Name", "Payload", "NodeFilter", "ServiceFilter", "TagFilter", "Version", "LTime")

}
