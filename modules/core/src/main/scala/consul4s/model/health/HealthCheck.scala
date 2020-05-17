package consul4s.model.health

import consul4s.model.CheckStatus

final case class HealthCheck(
  Node: String,
  CheckID: String,
  Name: String,
  Status: CheckStatus,
  Notes: String,
  Output: String,
  ServiceID: String,
  ServiceName: String,
  ServiceTags: Option[List[String]],
  Type: String,
  Definition: Option[HealthCheckDefinition]
)
