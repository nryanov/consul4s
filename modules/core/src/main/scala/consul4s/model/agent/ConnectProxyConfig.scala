package consul4s.model.agent

final case class ConnectProxyConfig(
  proxyServiceId: String,
  targetServiceId: String,
  targetServiceName: String,
  contentHash: String,
  config: Map[String, String],
  upstreams: List[Upstream]
)
