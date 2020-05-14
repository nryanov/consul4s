package consul4s.model.agent

import consul4s.model.Status
import consul4s.model.health.HealthCheckDefinition

final case class AgentCheck(
  Node: String,
  CheckID: String,
  Name: String,
  Status: Status,
  Notes: String,
  Output: String,
  ServiceID: String,
  ServiceName: String,
  Type: String,
  Definition: HealthCheckDefinition
)
