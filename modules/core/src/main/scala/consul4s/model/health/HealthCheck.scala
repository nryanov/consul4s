package consul4s.model.health

import consul4s.model.CheckStatus
import HealthCheck._

/**
 * @param Node
 * @param CheckID
 * @param Name
 * @param Status
 * @param Notes
 * @param Output
 * @param ServiceID
 * @param ServiceName
 * @param ServiceTags
 * @param Type
 * @param Definition - The Definition field can be provided with details for a TCP or HTTP health check.
 * @param CreateIndex
 * @param ModifyIndex
 */
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
