package consul4s.model.agent

import consul4s.model.ServiceKind
import consul4s.model.catalog.ServiceAddress

final case class AgentServiceRegistration(
  kind: Option[ServiceKind],
  id: Option[String],
  name: Option[String],
  tags: Option[List[String]],
  port: Option[Int],
  address: Option[String],
  taggedAddresses: Option[Map[String, ServiceAddress]],
  enableTagOverride: Option[Boolean],
  meta: Option[Map[String, String]],
  weights: AgentWeights,
  check: AgentServiceCheck,
  checks: List[AgentServiceCheck],
  proxy: Option[AgentServiceConnectProxyConfig],
  connect: Option[AgentServiceConnect],
  namespace: Option[String]
)
