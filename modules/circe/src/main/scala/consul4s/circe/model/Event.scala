package consul4s.circe.model

import consul4s.model.event.UserEvent
import io.circe.Decoder.Result
import io.circe._

trait Event {
  implicit val userEventDecoder: Decoder[UserEvent] = new Decoder[UserEvent] {
    override def apply(c: HCursor): Result[UserEvent] = for {
      id <- c.downField("ID").as[String]
      name <- c.downField("Name").as[String]
      payload <- c.downField("Payload").as[Option[String]]
      nodeFilter <- c.downField("NodeFilter").as[String]
      serviceFilter <- c.downField("ServiceFilter").as[String]
      tagFilter <- c.downField("TagFilter").as[String]
      version <- c.downField("Version").as[Long]
      lTime <- c.downField("LTime").as[Long]
    } yield UserEvent(
      id,
      name,
      payload,
      nodeFilter,
      serviceFilter,
      tagFilter,
      version,
      lTime
    )
  }
}
