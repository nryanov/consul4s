package consul4s.circe.model

import consul4s.model.event.UserEvent
import io.circe._
import io.circe.generic.semiauto._

trait Event {
  implicit val userEventEncoder: Encoder[UserEvent] = deriveEncoder[UserEvent]
  implicit val userEventDecoder: Decoder[UserEvent] = deriveDecoder[UserEvent]
}
