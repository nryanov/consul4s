package consul4s.model.catalog

import consul4s.model.agent.{AgentCheck, AgentService}
import consul4s.model.health.HealthCheck

final case class CatalogRegistration(
  id: String,
  node: String,
  address: String,
  taggedAddresses: Map[String, String],
  nodeMeta: Map[String, String],
  datacenter: String,
  service: Option[AgentService],
  check: Option[AgentCheck],
  checks: List[HealthCheck],
  skipNodeUpdate: Boolean
)
