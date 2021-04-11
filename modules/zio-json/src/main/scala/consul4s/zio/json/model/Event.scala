package consul4s.zio.json.model

import consul4s.model.event.UserEvent
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Event {
  private[zio] case class UserEventRepr(
    ID: String,
    Name: String,
    Payload: Option[String],
    NodeFilter: String,
    ServiceFilter: String,
    TagFilter: String,
    Version: Long,
    LTime: Long
  )

  implicit val userEventCodec: JsonCodec[UserEvent] = ConverterMacros.derive[UserEventRepr, UserEvent]
}
