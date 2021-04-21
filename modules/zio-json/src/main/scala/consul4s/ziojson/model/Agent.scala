package consul4s.ziojson.model

import consul4s.model.agent._
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Agent { this: Common with Health =>
  implicit val scriptCheckCodec: JsonCodec[ScriptCheck] = DeriveJsonCodec.gen[ScriptCheck]
  implicit val httpCheckCodec: JsonCodec[HttpCheck] = DeriveJsonCodec.gen[HttpCheck]
  implicit val tcpCheckCodec: JsonCodec[TCPCheck] = DeriveJsonCodec.gen[TCPCheck]
  implicit val ttlCheckCodec: JsonCodec[TTLCheck] = DeriveJsonCodec.gen[TTLCheck]
  implicit val dockerCheckCodec: JsonCodec[DockerCheck] = DeriveJsonCodec.gen[DockerCheck]
  implicit val grpcCheckCodec: JsonCodec[GRpcCheck] = DeriveJsonCodec.gen[GRpcCheck]
  implicit val aliasCheckCodec: JsonCodec[AliasCheck] = DeriveJsonCodec.gen[AliasCheck]
  implicit val checkCodec: JsonCodec[Check] = DeriveJsonCodec.gen[Check]
  implicit val serviceScriptCheckCodec: JsonCodec[ServiceScriptCheck] = DeriveJsonCodec.gen[ServiceScriptCheck]
  implicit val serviceHttpCheckCodec: JsonCodec[ServiceHttpCheck] = DeriveJsonCodec.gen[ServiceHttpCheck]
  implicit val serviceTCPCheckCodec: JsonCodec[ServiceTCPCheck] = DeriveJsonCodec.gen[ServiceTCPCheck]
  implicit val serviceTTLCheckCodec: JsonCodec[ServiceTTLCheck] = DeriveJsonCodec.gen[ServiceTTLCheck]
  implicit val serviceDockerCheckCodec: JsonCodec[ServiceDockerCheck] = DeriveJsonCodec.gen[ServiceDockerCheck]
  implicit val serviceGRpcCheckCodec: JsonCodec[ServiceGRpcCheck] = DeriveJsonCodec.gen[ServiceGRpcCheck]
  implicit val serviceAliasCheckCodec: JsonCodec[ServiceAliasCheck] = DeriveJsonCodec.gen[ServiceAliasCheck]
  implicit val serviceCheckCodec: JsonCodec[ServiceCheck] = DeriveJsonCodec.gen[ServiceCheck]
  implicit val memberInfoCodec: JsonCodec[MemberInfo] = DeriveJsonCodec.gen[MemberInfo]
  implicit val checkUpdateCodec: JsonCodec[CheckUpdate] = DeriveJsonCodec.gen[CheckUpdate]
  implicit val serviceCodec: JsonCodec[Service] = DeriveJsonCodec.gen[Service]
  implicit val newServiceCodec: JsonCodec[NewService] = DeriveJsonCodec.gen[NewService]
  implicit val aggregatedServiceStatusCodec: JsonCodec[AggregatedServiceStatus] = DeriveJsonCodec.gen[AggregatedServiceStatus]
  implicit val tokenCodec: JsonCodec[Token] = DeriveJsonCodec.gen[Token]
  implicit val upstreamDestTypeCodec: JsonCodec[UpstreamDestType] = JsonCodec.string.xmap(v => UpstreamDestType.withValue(v), _.value)
}
