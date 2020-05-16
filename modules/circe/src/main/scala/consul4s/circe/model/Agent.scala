package consul4s.circe.model

import consul4s.model.agent._
import io.circe._
import io.circe.syntax._
import io.circe.Decoder.Result
import io.circe.generic.semiauto._
import cats.syntax.functor._

trait Agent { this: Common =>
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

  implicit val scriptCheckEncoder: Encoder[ScriptCheck] = deriveEncoder[ScriptCheck]
  implicit val httpCheckEncoder: Encoder[HttpCheck] = deriveEncoder[HttpCheck]
  implicit val tcpCheckEncoder: Encoder[TCPCheck] = deriveEncoder[TCPCheck]
  implicit val ttlCheckEncoder: Encoder[TTLCheck] = deriveEncoder[TTLCheck]
  implicit val dockerCheckEncoder: Encoder[DockerCheck] = deriveEncoder[DockerCheck]
  implicit val gRPCCheckEncoder: Encoder[GRpcCheck] = deriveEncoder[GRpcCheck]
  implicit val aliasCheckEncoder: Encoder[AliasCheck] = deriveEncoder[AliasCheck]

  implicit val scriptCheckDecoder: Decoder[ScriptCheck] = deriveDecoder[ScriptCheck]
  implicit val httpCheckDecoder: Decoder[HttpCheck] = deriveDecoder[HttpCheck]
  implicit val tcpCheckDecoder: Decoder[TCPCheck] = deriveDecoder[TCPCheck]
  implicit val ttlCheckDecoder: Decoder[TTLCheck] = deriveDecoder[TTLCheck]
  implicit val dockerCheckDecoder: Decoder[DockerCheck] = deriveDecoder[DockerCheck]
  implicit val gRPCCheckDecoder: Decoder[GRpcCheck] = deriveDecoder[GRpcCheck]
  implicit val aliasCheckDecoder: Decoder[AliasCheck] = deriveDecoder[AliasCheck]

  implicit val checkEncoder: Encoder[Check] = Encoder.instance {
    case v: ScriptCheck => v.asJson
    case v: HttpCheck   => v.asJson
    case v: TCPCheck    => v.asJson
    case v: TTLCheck    => v.asJson
    case v: DockerCheck => v.asJson
    case v: GRpcCheck   => v.asJson
    case v: AliasCheck  => v.asJson
  }

  implicit val decodeEvent: Decoder[Check] =
    List[Decoder[Check]](
      scriptCheckDecoder.widen,
      httpCheckDecoder.widen,
      tcpCheckDecoder.widen,
      ttlCheckDecoder.widen,
      dockerCheckDecoder.widen,
      gRPCCheckDecoder.widen,
      aliasCheckDecoder.widen
    ).reduceLeft(_.or(_))

  implicit val memberInfoDecoder: Decoder[MemberInfo] = deriveDecoder[MemberInfo]
  implicit val checkInfoDecoder: Decoder[CheckInfo] = deriveDecoder[CheckInfo]
  implicit val checkUpdateEncoder: Encoder[CheckUpdate] = deriveEncoder[CheckUpdate]

  implicit val serviceEncoder: Encoder[Service] = deriveEncoder[Service]
  implicit val serviceDecoder: Decoder[Service] = deriveDecoder[Service]
}
