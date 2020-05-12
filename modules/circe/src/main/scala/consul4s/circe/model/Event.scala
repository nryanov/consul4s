package consul4s.circe.model

import consul4s.model.event.UserEvent
import io.circe.Decoder.Result
import io.circe._

trait Event {
  implicit val eventDecoder: Decoder[UserEvent] = new Decoder[UserEvent] {
    override def apply(c: HCursor): Result[UserEvent] = for {
      id <- c.downField("ID").as[String]
      name <- c.downField("Name").as[String]
      payload <- c.downField("Payload").as[Option[Array[Byte]]]
      nodeFilter <- c.downField("NodeFilter").as[String]
      serviceFilter <- c.downField("ServiceFilter").as[String]
      tagFilter <- c.downField("TagFilter").as[String]
      version <- c.downField("Version").as[Int]
      lTime <- c.downField("LTime").as[Long]
    } yield UserEvent(id, name, payload, nodeFilter, serviceFilter, tagFilter, version, lTime)
  }

  implicit val eventEncoder: Encoder[UserEvent] = new Encoder[UserEvent] {
    override def apply(a: UserEvent): Json = Json.obj(
      ("ID", Json.fromString(a.id)),
      ("Name", Json.fromString(a.name)),
      ("Payload", Json.fromValues(a.payload.getOrElse(Array()).map(Json.fromInt(_)))),
      ("NodeFilter", Json.fromString(a.nodeFilter)),
      ("ServiceFilter", Json.fromString(a.serviceFilter)),
      ("TagFilter", Json.fromString(a.tagFilter)),
      ("Version", Json.fromInt(a.version)),
      ("LTime", Json.fromLong(a.lTime))
    )
  }
}
