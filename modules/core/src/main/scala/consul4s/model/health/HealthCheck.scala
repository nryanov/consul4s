package consul4s.model.health

import consul4s.model.CheckStatus

final case class HealthCheck(
  Node: String,
  CheckId: String,
  Name: String,
  Status: CheckStatus,
  Notes: String,
  Output: String,
  ServiceId: String,
  ServiceName: String,
  ServiceTags: List[String],
  Type: String,
  Definition: Option[HealthCheckDefinition],
  CreateIndex: Long,
  ModifyIndex: Long
)
