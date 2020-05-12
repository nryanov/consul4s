package consul4s.model.agent

import consul4s.model.ServiceKind
import consul4s.model.catalog.ServiceAddress

final case class AgentService(
  kind: Option[ServiceKind],
  id: String,
  service: String,
  tags: List[String],
  meta: Map[String, String],
  port: Int,
  address: String,
  taggedAddresses: Option[Map[String, ServiceAddress]],
  weights: AgentWeights,
  enableTagOverride: Boolean,
  createIndex: Option[Long],
  modifyIndex: Option[Long],
  contentHash: Option[String],
  proxy: Option[AgentServiceConnectProxyConfig],
  connect: Option[AgentServiceConnect]
)
