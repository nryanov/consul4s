package consul4s.model.agent

final case class AgentServiceConnectProxyConfig(
  DestinationServiceName: Option[String],
  DestinationServiceID: Option[String],
  LocalServiceAddress: Option[String],
  LocalServicePort: Option[Int],
  Config: Option[Map[String, String]],
  Upstreams: Option[Upstream]
)
