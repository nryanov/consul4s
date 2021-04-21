package consul4s.ziojson.model

import consul4s.model.event.UserEvent
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Event {
  implicit val userEventCodec: JsonCodec[UserEvent] = DeriveJsonCodec.gen[UserEvent]
}
