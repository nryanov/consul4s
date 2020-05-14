package consul4s.model.agent

import consul4s.model._
import consul4s.model.ServiceKind
import consul4s.model.catalog.ServiceAddress

final case class AgentService(
  Service: String,
  ID: String = DEFAULT_VALUE,
  Kind: Option[ServiceKind] = Some(ServiceKind.Typical),
  Tags: Option[List[String]] = None,
  Meta: Option[Map[String, String]] = None,
  Port: Int = DEFAULT_PORT,
  Address: String = DEFAULT_VALUE,
  TaggedAddresses: Option[Map[String, ServiceAddress]] = None,
  Weights: Option[AgentWeights] = None,
  EnableTagOverride: Boolean = false,
  Proxy: Option[AgentServiceConnectProxyConfig] = None,
  Connect: Option[AgentServiceConnect] = None
)
