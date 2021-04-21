package consul4s.ziojson.model

import consul4s.model.agent._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Agent { this: Common with Health =>
  implicit val scriptCheckEncoder: JsonEncoder[ScriptCheck] = DeriveJsonEncoder.gen[ScriptCheck]
  implicit val httpCheckEncoder: JsonEncoder[HttpCheck] = DeriveJsonEncoder.gen[HttpCheck]
  implicit val tcpCheckEncoder: JsonEncoder[TCPCheck] = DeriveJsonEncoder.gen[TCPCheck]
  implicit val ttlCheckEncoder: JsonEncoder[TTLCheck] = DeriveJsonEncoder.gen[TTLCheck]
  implicit val dockerCheckEncoder: JsonEncoder[DockerCheck] = DeriveJsonEncoder.gen[DockerCheck]
  implicit val grpcCheckEncoder: JsonEncoder[GRpcCheck] = DeriveJsonEncoder.gen[GRpcCheck]
  implicit val aliasCheckEncoder: JsonEncoder[AliasCheck] = DeriveJsonEncoder.gen[AliasCheck]
  implicit val checkEncoder: JsonEncoder[Check] = DeriveJsonEncoder.gen[Check]
  implicit val serviceScriptCheckEncoder: JsonEncoder[ServiceScriptCheck] = DeriveJsonEncoder.gen[ServiceScriptCheck]
  implicit val serviceHttpCheckEncoder: JsonEncoder[ServiceHttpCheck] = DeriveJsonEncoder.gen[ServiceHttpCheck]
  implicit val serviceTCPCheckEncoder: JsonEncoder[ServiceTCPCheck] = DeriveJsonEncoder.gen[ServiceTCPCheck]
  implicit val serviceTTLCheckEncoder: JsonEncoder[ServiceTTLCheck] = DeriveJsonEncoder.gen[ServiceTTLCheck]
  implicit val serviceDockerCheckEncoder: JsonEncoder[ServiceDockerCheck] = DeriveJsonEncoder.gen[ServiceDockerCheck]
  implicit val serviceGRpcCheckEncoder: JsonEncoder[ServiceGRpcCheck] = DeriveJsonEncoder.gen[ServiceGRpcCheck]
  implicit val serviceAliasCheckEncoder: JsonEncoder[ServiceAliasCheck] = DeriveJsonEncoder.gen[ServiceAliasCheck]
  implicit val serviceCheckEncoder: JsonEncoder[ServiceCheck] = DeriveJsonEncoder.gen[ServiceCheck]
  implicit val memberInfoEncoder: JsonEncoder[MemberInfo] = DeriveJsonEncoder.gen[MemberInfo]
  implicit val checkUpdateEncoder: JsonEncoder[CheckUpdate] = DeriveJsonEncoder.gen[CheckUpdate]
  implicit val serviceEncoder: JsonEncoder[Service] = DeriveJsonEncoder.gen[Service]
  implicit val newServiceEncoder: JsonEncoder[NewService] = DeriveJsonEncoder.gen[NewService]
  implicit val aggregatedServiceStatusEncoder: JsonEncoder[AggregatedServiceStatus] = DeriveJsonEncoder.gen[AggregatedServiceStatus]
  implicit val tokenEncoder: JsonEncoder[Token] = DeriveJsonEncoder.gen[Token]

  implicit val scriptCheckDecoder: JsonDecoder[ScriptCheck] = DeriveJsonDecoder.gen[ScriptCheck]
  implicit val httpCheckDecoder: JsonDecoder[HttpCheck] = DeriveJsonDecoder.gen[HttpCheck]
  implicit val tcpCheckDecoder: JsonDecoder[TCPCheck] = DeriveJsonDecoder.gen[TCPCheck]
  implicit val ttlCheckDecoder: JsonDecoder[TTLCheck] = DeriveJsonDecoder.gen[TTLCheck]
  implicit val dockerCheckDecoder: JsonDecoder[DockerCheck] = DeriveJsonDecoder.gen[DockerCheck]
  implicit val grpcCheckDecoder: JsonDecoder[GRpcCheck] = DeriveJsonDecoder.gen[GRpcCheck]
  implicit val aliasCheckDecoder: JsonDecoder[AliasCheck] = DeriveJsonDecoder.gen[AliasCheck]
  implicit val checkDecoder: JsonDecoder[Check] = DeriveJsonDecoder.gen[Check]
  implicit val serviceScriptCheckDecoder: JsonDecoder[ServiceScriptCheck] = DeriveJsonDecoder.gen[ServiceScriptCheck]
  implicit val serviceHttpCheckDecoder: JsonDecoder[ServiceHttpCheck] = DeriveJsonDecoder.gen[ServiceHttpCheck]
  implicit val serviceTCPCheckDecoder: JsonDecoder[ServiceTCPCheck] = DeriveJsonDecoder.gen[ServiceTCPCheck]
  implicit val serviceTTLCheckDecoder: JsonDecoder[ServiceTTLCheck] = DeriveJsonDecoder.gen[ServiceTTLCheck]
  implicit val serviceDockerCheckDecoder: JsonDecoder[ServiceDockerCheck] = DeriveJsonDecoder.gen[ServiceDockerCheck]
  implicit val serviceGRpcCheckDecoder: JsonDecoder[ServiceGRpcCheck] = DeriveJsonDecoder.gen[ServiceGRpcCheck]
  implicit val serviceAliasCheckDecoder: JsonDecoder[ServiceAliasCheck] = DeriveJsonDecoder.gen[ServiceAliasCheck]
  implicit val serviceCheckDecoder: JsonDecoder[ServiceCheck] = DeriveJsonDecoder.gen[ServiceCheck]
  implicit val memberInfoDecoder: JsonDecoder[MemberInfo] = DeriveJsonDecoder.gen[MemberInfo]
  implicit val checkUpdateDecoder: JsonDecoder[CheckUpdate] = DeriveJsonDecoder.gen[CheckUpdate]
  implicit val serviceDecoder: JsonDecoder[Service] = DeriveJsonDecoder.gen[Service]
  implicit val newServiceDecoder: JsonDecoder[NewService] = DeriveJsonDecoder.gen[NewService]
  implicit val aggregatedServiceStatusDecoder: JsonDecoder[AggregatedServiceStatus] = DeriveJsonDecoder.gen[AggregatedServiceStatus]
  implicit val tokenDecoder: JsonDecoder[Token] = DeriveJsonDecoder.gen[Token]

  implicit val upstreamDestTypeEncoder: JsonEncoder[UpstreamDestType] = JsonEncoder.string.contramap(_.value)
  implicit val upstreamDestTypeDecoder: JsonDecoder[UpstreamDestType] = JsonDecoder.string.map(v => UpstreamDestType.withValue(v))
}
