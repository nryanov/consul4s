package consul4s.model.health

import consul4s.model.CheckStatus
import HealthCheck._

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
  Definition: HealthCheckDefinition,
  CreateIndex: Long,
  ModifyIndex: Long
)

object HealthCheck {
  final case class HealthCheckDefinition(
    HTTP: Option[String],
    Header: Option[Map[String, String]],
    Method: Option[String],
    Body: Option[String],
    TLSSkipVerify: Option[Boolean],
    TCP: Option[String],
    Interval: Option[String],
    Timeout: Option[String],
    DeregisterCriticalServiceAfter: Option[String]
  )

}
