package consul4s.model.agent

import consul4s.model.ServiceKind
import consul4s.model.catalog.ServiceAddress

final case class AgentServiceRegistration(
  Kind: Option[ServiceKind],
  ID: Option[String],
  Name: Option[String],
  Tags: Option[List[String]],
  Port: Option[Int],
  Address: Option[String],
  TaggedAddresses: Option[Map[String, ServiceAddress]],
  EnableTagOverride: Option[Boolean],
  Meta: Option[Map[String, String]],
  Weights: AgentWeights,
  Checks: List[AgentServiceCheck],
  Proxy: Option[AgentServiceConnectProxyConfig],
  Connect: Option[AgentServiceConnect]
)
