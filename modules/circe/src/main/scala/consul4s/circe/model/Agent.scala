package consul4s.circe.model

import consul4s.model.agent._
import io.circe._
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

  implicit val agentAuthorizeEncoder: Encoder[AgentAuthorize] = deriveEncoder[AgentAuthorize]
  implicit val agentAuthorizeParamsEncoder: Encoder[AgentAuthorizeParams] = deriveEncoder[AgentAuthorizeParams]
  implicit val agentCheckEncoder: Encoder[AgentCheck] = deriveEncoder[AgentCheck]
  implicit val agentCheckRegistrationEncoder: Encoder[AgentCheckRegistration] = deriveEncoder[AgentCheckRegistration]
  implicit val agentMemberEncoder: Encoder[AgentMember] = deriveEncoder[AgentMember]
  implicit val agentServiceEncoder: Encoder[AgentService] = deriveEncoder[AgentService]
  implicit val agentServiceCheckEncoder: Encoder[AgentServiceCheck] = deriveEncoder[AgentServiceCheck]
  implicit val agentServiceChecksInfoEncoder: Encoder[AgentServiceChecksInfo] = deriveEncoder[AgentServiceChecksInfo]
  implicit val agentServiceConnectEncoder: Encoder[AgentServiceConnect] = deriveEncoder[AgentServiceConnect]
  implicit val agentServiceConnectProxyConfigEncoder: Encoder[AgentServiceConnectProxyConfig] =
    deriveEncoder[AgentServiceConnectProxyConfig]
  implicit val agentServiceRegistrationEncoder: Encoder[AgentServiceRegistration] = deriveEncoder[AgentServiceRegistration]
  implicit val agentTokenEncoder: Encoder[AgentToken] = deriveEncoder[AgentToken]
  implicit val agentWeightsEncoder: Encoder[AgentWeights] = deriveEncoder[AgentWeights]
  implicit val connectProxyConfigEncoder: Encoder[ConnectProxyConfig] = deriveEncoder[ConnectProxyConfig]
  implicit val membersOptsEncoder: Encoder[MembersOpts] = deriveEncoder[MembersOpts]
  implicit val serviceRegisterOptsEncoder: Encoder[ServiceRegisterOpts] = deriveEncoder[ServiceRegisterOpts]
  implicit val upstreamEncoder: Encoder[Upstream] = deriveEncoder[Upstream]

  implicit val agentAuthorizeDecoder: Decoder[AgentAuthorize] = deriveDecoder[AgentAuthorize]
  implicit val agentAuthorizeParamsDecoder: Decoder[AgentAuthorizeParams] = deriveDecoder[AgentAuthorizeParams]
  implicit val agentCheckDecoder: Decoder[AgentCheck] = deriveDecoder[AgentCheck]
  implicit val agentCheckRegistrationDecoder: Decoder[AgentCheckRegistration] = deriveDecoder[AgentCheckRegistration]
  implicit val agentMemberDecoder: Decoder[AgentMember] = deriveDecoder[AgentMember]
  implicit val agentServiceDecoder: Decoder[AgentService] = deriveDecoder[AgentService]
  implicit val agentServiceCheckDecoder: Decoder[AgentServiceCheck] = deriveDecoder[AgentServiceCheck]
  implicit val agentServiceChecksInfoDecoder: Decoder[AgentServiceChecksInfo] = deriveDecoder[AgentServiceChecksInfo]
  implicit val agentServiceConnectDecoder: Decoder[AgentServiceConnect] = deriveDecoder[AgentServiceConnect]
  implicit val agentServiceConnectProxyConfigDecoder: Decoder[AgentServiceConnectProxyConfig] =
    deriveDecoder[AgentServiceConnectProxyConfig]
  implicit val agentServiceRegistrationDecoder: Decoder[AgentServiceRegistration] = deriveDecoder[AgentServiceRegistration]
  implicit val agentTokenDecoder: Decoder[AgentToken] = deriveDecoder[AgentToken]
  implicit val agentWeightsDecoder: Decoder[AgentWeights] = deriveDecoder[AgentWeights]
  implicit val connectProxyConfigDecoder: Decoder[ConnectProxyConfig] = deriveDecoder[ConnectProxyConfig]
  implicit val membersOptsDecoder: Decoder[MembersOpts] = deriveDecoder[MembersOpts]
  implicit val serviceRegisterOptsDecoder: Decoder[ServiceRegisterOpts] = deriveDecoder[ServiceRegisterOpts]
  implicit val upstreamDecoder: Decoder[Upstream] = deriveDecoder[Upstream]
}
