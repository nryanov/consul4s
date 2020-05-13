package consul4s.model.catalog

import consul4s.model.agent.{AgentCheck, AgentService}
import consul4s.model.health.HealthCheck

final case class CatalogRegistration(
  id: Option[String] = None,
  node: String,
  address: String,
  taggedAddresses: Option[Map[String, String]] = None,
  nodeMeta: Option[Map[String, String]] = None,
  datacenter: Option[String] = None,
  service: Option[AgentService] = None,
  checks: Option[List[HealthCheck]] = None,
  skipNodeUpdate: Boolean = false
)
