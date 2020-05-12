package consul4s.model.agent

final case class AgentServiceConnectProxyConfig(
  destinationServiceName: Option[String],
  destinationServiceID: Option[String],
  localServiceAddress: Option[String],
  localServicePort: Option[Int],
  config: Option[Map[String, String]],
  upstreams: Option[Upstream]
  // todo
//  MeshGateway: Option[MeshGatewayConfig],
//  Expose: Option[ExposeConfig]
)
