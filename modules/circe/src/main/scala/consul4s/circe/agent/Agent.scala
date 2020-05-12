package consul4s.circe.agent

import consul4s.circe.Common
import consul4s.circe.catalog.Catalog
import consul4s.model.Status
import consul4s.model.agent._
import io.circe.Decoder.Result
import io.circe._

trait Agent extends Common { this: Catalog =>
  implicit val agentAuthorizeDecoder: Decoder[AgentAuthorize] = new Decoder[AgentAuthorize] {
    override def apply(c: HCursor): Result[AgentAuthorize] = for {
      authorized <- c.downField("Authorized").as[Boolean]
      reason <- c.downField("Reason").as[String]
    } yield AgentAuthorize(authorized, reason)
  }

  implicit val agentAuthorizeParamsDecoder: Decoder[AgentAuthorizeParams] = new Decoder[AgentAuthorizeParams] {
    override def apply(c: HCursor): Result[AgentAuthorizeParams] = for {
      target <- c.downField("Target").as[String]
      clientCertUri <- c.downField("ClientCertUri").as[String]
      clientCertSerial <- c.downField("ClientCertSerial").as[String]
    } yield AgentAuthorizeParams(target, clientCertUri, clientCertSerial)
  }

  implicit val agentCheckDecoder: Decoder[AgentCheck] = new Decoder[AgentCheck] {
    override def apply(c: HCursor): Result[AgentCheck] = for {
      node <- c.downField("Node").as[String]
      checkID <- c.downField("CheckID").as[String]
      name <- c.downField("Name").as[String]
      status <- c.downField("Status").as[Status]
      notes <- c.downField("Notes").as[String]
      output <- c.downField("Output").as[String]
      serviceID <- c.downField("ServiceID").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      tType <- c.downField("Type").as[String]
      definition <- c.downField("Definition").as[String]
      namespace <- c.downField("Namespace").as[String]
    } yield ???
  }

  implicit val agentCheckRegistrationDecoder: Decoder[AgentCheckRegistration] = new Decoder[AgentCheckRegistration] {
    override def apply(c: HCursor): Result[AgentCheckRegistration] = ???
  }

  implicit val agentMemberDecoder: Decoder[AgentMember] = new Decoder[AgentMember] {
    override def apply(c: HCursor): Result[AgentMember] = ???
  }

  implicit val agentServiceDecoder: Decoder[AgentService] = new Decoder[AgentService] {
    override def apply(c: HCursor): Result[AgentService] = ???
  }

  implicit val agentServiceCheckDecoder: Decoder[AgentServiceCheck] = new Decoder[AgentServiceCheck] {
    override def apply(c: HCursor): Result[AgentServiceCheck] = ???
  }

  implicit val agentServiceChecksInfoDecoder: Decoder[AgentServiceChecksInfo] = new Decoder[AgentServiceChecksInfo] {
    override def apply(c: HCursor): Result[AgentServiceChecksInfo] = ???
  }

  implicit val agentServiceConnectDecoder: Decoder[AgentServiceConnect] = new Decoder[AgentServiceConnect] {
    override def apply(c: HCursor): Result[AgentServiceConnect] = ???
  }

  implicit val agentServiceConnectProxyConfigDecoder: Decoder[AgentServiceConnectProxyConfig] =
    new Decoder[AgentServiceConnectProxyConfig] {
      override def apply(c: HCursor): Result[AgentServiceConnectProxyConfig] = ???
    }

  implicit val agentServiceRegistrationDecoder: Decoder[AgentServiceRegistration] = new Decoder[AgentServiceRegistration] {
    override def apply(c: HCursor): Result[AgentServiceRegistration] = ???
  }

  implicit val agentTokenDecoder: Decoder[AgentToken] = new Decoder[AgentToken] {
    override def apply(c: HCursor): Result[AgentToken] = ???
  }

  implicit val agentWeightsDecoder: Decoder[AgentWeights] = new Decoder[AgentWeights] {
    override def apply(c: HCursor): Result[AgentWeights] = ???
  }

  implicit val connectProxyConfigDecoder: Decoder[ConnectProxyConfig] = new Decoder[ConnectProxyConfig] {
    override def apply(c: HCursor): Result[ConnectProxyConfig] = ???
  }

  implicit val membersOptsDecoder: Decoder[MembersOpts] = new Decoder[MembersOpts] {
    override def apply(c: HCursor): Result[MembersOpts] = ???
  }

  implicit val serviceRegisterOptsDecoder: Decoder[ServiceRegisterOpts] = new Decoder[ServiceRegisterOpts] {
    override def apply(c: HCursor): Result[ServiceRegisterOpts] = ???
  }

  implicit val upstreamDecoder: Decoder[Upstream] = new Decoder[Upstream] {
    override def apply(c: HCursor): Result[Upstream] = ???
  }

  implicit val upstreamDestTypeDecoder: Decoder[UpstreamDestType] = new Decoder[UpstreamDestType] {
    override def apply(c: HCursor): Result[UpstreamDestType] = ???
  }
}
