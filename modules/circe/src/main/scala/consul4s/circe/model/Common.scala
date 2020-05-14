package consul4s.circe.model

import consul4s.model.{CheckStatus, ServiceKind}
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}

trait Common {
  implicit val statusDecoder: Decoder[CheckStatus] = new Decoder[CheckStatus] {
    override def apply(c: HCursor): Result[CheckStatus] = for {
      value <- c.as[String]
    } yield CheckStatus.withValue(value)
  }

  implicit val statusEncoder: Encoder[CheckStatus] = new Encoder[CheckStatus] {
    override def apply(a: CheckStatus): Json = Json.fromString(a.value)
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
