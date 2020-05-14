package consul4s.model.catalog

import consul4s.model.agent.AgentService
import consul4s.model.health.HealthCheck

final case class CatalogRegistration(
  Node: String,
  Address: String,
  Service: AgentService,
  ID: Option[String] = None,
  TaggedAddresses: Option[Map[String, String]] = None,
  NodeMeta: Option[Map[String, String]] = None,
  Datacenter: Option[String] = None,
  Checks: Option[List[HealthCheck]] = None,
  SkipNodeUpdate: Boolean = false
)
