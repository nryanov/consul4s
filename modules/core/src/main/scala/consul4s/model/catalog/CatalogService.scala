package consul4s.model.catalog

import consul4s.model.agent.AgentServiceConnectProxyConfig
import consul4s.model.health.HealthCheck
import consul4s.model._

final case class CatalogService(
  ID: String = DEFAULT_VALUE,
  Node: String = DEFAULT_VALUE,
  Address: String = DEFAULT_VALUE,
  Datacenter: String = DEFAULT_VALUE,
  TaggedAddresses: Option[Map[String, String]] = None,
  NodeMeta: Option[Map[String, String]] = None,
  ServiceId: String = DEFAULT_VALUE,
  ServiceName: String = DEFAULT_VALUE,
  ServiceAddress: String = DEFAULT_VALUE,
  ServiceTaggedAddresses: Map[String, ServiceAddress],
  ServiceTags: List[String],
  ServiceMeta: Map[String, String],
  ServicePort: Int,
  ServiceWeights: Weights,
  ServiceEnableTagOverride: Boolean,
  ServiceProxy: Option[AgentServiceConnectProxyConfig],
  Checks: List[HealthCheck]
)
