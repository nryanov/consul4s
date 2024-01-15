package consul4s.circe.model

import consul4s.model._
import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json, KeyDecoder, KeyEncoder}

trait Common {
  implicit val checkStatusDecoder: Decoder[CheckStatus] = new Decoder[CheckStatus] {
    override def apply(c: HCursor): Result[CheckStatus] =
      c.as[String].flatMap {
        case CheckStatus(value) => Right(value)
        case str                => Left(DecodingFailure(s"Can't convert $str to CheckStatus", c.history))
      }
  }

  implicit val checkStatusEncoder: Encoder[CheckStatus] = new Encoder[CheckStatus] {
    override def apply(a: CheckStatus): Json = Json.fromString(a.value)
  }

  implicit val checkStatusKeyDecoder: KeyDecoder[CheckStatus] = new KeyDecoder[CheckStatus] {
    override def apply(key: String): Option[CheckStatus] = CheckStatus.unapply(key)
  }

  implicit val checkStatusKeyEncoder: KeyEncoder[CheckStatus] = new KeyEncoder[CheckStatus] {
    override def apply(status: CheckStatus): String = status.value
  }

  implicit val serviceKindDecoder: Decoder[ServiceKind] = new Decoder[ServiceKind] {
    override def apply(c: HCursor): Result[ServiceKind] =
      c.as[String].flatMap {
        case ServiceKind(value) => Right(value)
        case str                => Left(DecodingFailure(s"Can't convert $str to ServiceKind", c.history))
      }
  }

  implicit val serviceKindEncoder: Encoder[ServiceKind] = new Encoder[ServiceKind] {
    override def apply(a: ServiceKind): Json = Json.fromString(a.value)
  }

  implicit val sessionBehaviorDecoder: Decoder[SessionBehavior] = new Decoder[SessionBehavior] {
    override def apply(c: HCursor): Result[SessionBehavior] =
      c.as[String].flatMap {
        case SessionBehavior(value) => Right(value)
        case str                    => Left(DecodingFailure(s"Can't convert $str to SessionBehavior", c.history))
      }
  }

  implicit val sessionBehaviorEncoder: Encoder[SessionBehavior] = new Encoder[SessionBehavior] {
    override def apply(a: SessionBehavior): Json = Json.fromString(a.value)
  }
}
