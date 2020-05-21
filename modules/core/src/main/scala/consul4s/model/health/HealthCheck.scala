package consul4s.model.health

import consul4s.model.CheckStatus
import HealthCheck._

/**
 *
 * @param node
 * @param checkId
 * @param name
 * @param status
 * @param notes
 * @param output
 * @param serviceId
 * @param serviceName
 * @param serviceTags
 * @param `type`
 * @param definition - The Definition field can be provided with details for a TCP or HTTP health check.
 * @param createIndex
 * @param modifyIndex
 */
final case class HealthCheck(
  node: String,
  checkId: String,
  name: String,
  status: CheckStatus,
  notes: String,
  output: String,
  serviceId: String,
  serviceName: String,
  serviceTags: Option[List[String]],
  `type`: String,
  definition: HealthCheckDefinition,
  createIndex: Long,
  modifyIndex: Long
)

object HealthCheck {
  final case class HealthCheckDefinition(
    http: Option[String],
    header: Option[Map[String, String]],
    method: Option[String],
    body: Option[String],
    tlsSkipVerify: Option[Boolean],
    tcp: Option[String],
    interval: Option[String],
    timeout: Option[String],
    deregisterCriticalServiceAfter: Option[String]
  )

}
