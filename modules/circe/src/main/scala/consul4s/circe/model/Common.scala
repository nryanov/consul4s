package consul4s.circe.model

import consul4s.model.{ServiceKind, Status}
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}

trait Common {
  implicit val statusDecoder: Decoder[Status] = new Decoder[Status] {
    override def apply(c: HCursor): Result[Status] = for {
      value <- c.as[String]
    } yield Status.withValue(value)
  }

  implicit val statusEncoder: Encoder[Status] = new Encoder[Status] {
    override def apply(a: Status): Json = Json.fromString(a.value)
  }

  implicit val serviceKindDecoder: Decoder[ServiceKind] = new Decoder[ServiceKind] {
    override def apply(c: HCursor): Result[ServiceKind] = for {
      value <- c.as[String]
    } yield ServiceKind.withValue(value)
  }

  implicit val serviceKindEncoder: Encoder[ServiceKind] = new Encoder[ServiceKind] {
    override def apply(a: ServiceKind): Json = Json.fromString(a.value)
  }
}
