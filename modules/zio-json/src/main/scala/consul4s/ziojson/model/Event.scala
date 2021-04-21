package consul4s.ziojson.model

import consul4s.model.event.UserEvent
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Event {
  implicit val userEventEncoder: JsonEncoder[UserEvent] = DeriveJsonEncoder.gen[UserEvent]

  implicit val userEventDecoder: JsonDecoder[UserEvent] = DeriveJsonDecoder.gen[UserEvent]
}
