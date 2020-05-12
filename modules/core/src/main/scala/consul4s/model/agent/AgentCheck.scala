package consul4s.model.agent

import consul4s.model.Status
import consul4s.model.health.HealthCheckDefinition

final case class AgentCheck(
  node: String,
  checkID: String,
  name: String,
  status: Status,
  notes: String,
  output: String,
  serviceID: String,
  serviceName: String,
  `type`: String,
  definition: HealthCheckDefinition,
  namespace: Option[String]
)
