package consul4s.circe

import consul4s.model.{ServiceKind, Status}
import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor}

trait Common {
  implicit val statusDecoder: Decoder[Status] = new Decoder[Status] {
    override def apply(c: HCursor): Result[Status] = for {
      value <- c.as[String]
    } yield Status.withValue(value)
  }

  implicit val serviceKindDecoder: Decoder[ServiceKind] = new Decoder[ServiceKind] {
    override def apply(c: HCursor): Result[ServiceKind] = for {
      value <- c.as[String]
    } yield ServiceKind.withValue(value)
  }
}
