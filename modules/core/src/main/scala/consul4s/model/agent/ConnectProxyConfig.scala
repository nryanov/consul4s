package consul4s.model.agent

final case class ConnectProxyConfig(
  ProxyServiceId: String,
  TargetServiceId: String,
  TargetServiceName: String,
  ContentHash: String,
  Config: Map[String, String],
  Upstreams: List[Upstream]
)
