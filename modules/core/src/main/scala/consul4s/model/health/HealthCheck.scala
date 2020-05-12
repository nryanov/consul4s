package consul4s.model.health

import consul4s.model.Status

final case class HealthCheck(
  node: String,
  checkId: String,
  name: String,
  status: Status,
  notes: String,
  output: String,
  serviceId: String,
  serviceName: String,
  serviceTags: List[String],
  `type`: String,
  namespace: Option[String],
  definition: Option[HealthCheckDefinition],
  createIndex: Long,
  modifyIndex: Long
)
