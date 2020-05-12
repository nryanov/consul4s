package consul4s.model.catalog

import consul4s.model.agent.AgentServiceConnectProxyConfig
import consul4s.model.health.HealthCheck

final case class CatalogService(
  id: String,
  node: String,
  address: String,
  datacenter: String,
  taggedAddresses: Map[String, String],
  nodeMeta: Map[String, String],
  serviceId: String,
  serviceName: String,
  serviceAddress: String,
  serviceTaggedAddresses: Map[String, ServiceAddress],
  serviceTags: List[String],
  serviceMeta: Map[String, String],
  servicePort: Int,
  serviceWeights: Weights,
  serviceEnableTagOverride: Boolean,
  serviceProxy: Option[AgentServiceConnectProxyConfig],
  createIndex: Long,
  checks: List[HealthCheck],
  modifyIndex: Long,
  namespace: Option[String]
)
