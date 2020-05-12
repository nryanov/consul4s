package consul4s.json4s.model

import consul4s.model.event.UserEvent
import org.json4s.JsonAST.{JArray, JField, JInt, JLong, JString}
import org.json4s.{CustomSerializer, JObject}

trait Event {
  val eventSerializer = new CustomSerializer[UserEvent](
    implicit format =>
      (
        {
          case json: JObject =>
            UserEvent(
              (json \ "ID").extract[String],
              (json \ "Name").extract[String],
              (json \ "Payload").extract[Option[Array[Byte]]],
              (json \ "NodeFilter").extract[String],
              (json \ "ServiceFilter").extract[String],
              (json \ "TagFilter").extract[String],
              (json \ "Version").extract[Int],
              (json \ "LTime").extract[Long]
            )
        }, {
          case o: UserEvent =>
            JObject(
              JField("ID", JString(o.id)),
              JField("Name", JString(o.name)),
              JField("Payload", JArray(o.payload.getOrElse(Array()).map(byte => JInt(byte.toInt)).toList)),
              JField("NodeFilter", JString(o.nodeFilter)),
              JField("ServiceFilter", JString(o.serviceFilter)),
              JField("TagFilter", JString(o.tagFilter)),
              JField("Version", JInt(o.version)),
              JField("LTime", JLong(o.lTime))
            )
        }
      )
  )
}
