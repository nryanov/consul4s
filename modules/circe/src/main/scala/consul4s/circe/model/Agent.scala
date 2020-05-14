package consul4s.circe.model

import consul4s.model.agent._
import io.circe._
import io.circe.syntax._
import io.circe.Decoder.Result
import io.circe.generic.semiauto._

trait Agent { this: Catalog with Health with Common =>
  implicit val upstreamDestTypeDecoder: Decoder[UpstreamDestType] = new Decoder[UpstreamDestType] {
    override def apply(c: HCursor): Result[UpstreamDestType] = for {
      value <- c.as[String]
    } yield UpstreamDestType.withValue(value)
  }

  implicit val upstreamDestTypeEncoder: Encoder[UpstreamDestType] = new Encoder[UpstreamDestType] {
    override def apply(a: UpstreamDestType): Json = Json.fromString(a.value)
  }

  implicit val weightsDecoder: Decoder[Weights] = deriveDecoder[Weights]
  implicit val weightsEncoder: Encoder[Weights] = deriveEncoder[Weights]

  implicit val taggedAddressDecoder: Decoder[TaggedAddress] = deriveDecoder[TaggedAddress]
  implicit val taggedAddressEncoder: Encoder[TaggedAddress] = deriveEncoder[TaggedAddress]

  implicit val checkEncoder: Encoder[Check] = Encoder.instance {
    case v: ScriptCheck => v.asJson
    case v: HttpCheck   => v.asJson
    case v: TCPCheck    => v.asJson
    case v: TTLCheck    => v.asJson
    case v: DockerCheck => v.asJson
    case v: GRpcCheck   => v.asJson
    case v: AliasCheck  => v.asJson
  }
}
