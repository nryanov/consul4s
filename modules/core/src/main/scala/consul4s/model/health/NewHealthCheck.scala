package consul4s.model.health

import consul4s.model.CheckStatus
import NewHealthCheck._

/**
 * @param node
 * @param name
 * @param checkId
 * @param status
 * @param notes
 * @param output
 * @param serviceId
 * @param serviceName
 * @param serviceTags
 * @param `type`
 * @param definition - The Definition field can be provided with details for a TCP or HTTP health check.
 */
final case class NewHealthCheck(
  node: String,
  name: String,
  checkId: Option[String] = None,
  status: Option[CheckStatus] = None,
  notes: Option[String] = None,
  output: Option[String] = None,
  serviceId: Option[String] = None,
  serviceName: Option[String] = None,
  serviceTags: Option[List[String]] = None,
  `type`: Option[String] = None,
  definition: Option[NewHealthCheckDefinition] = None
)

object NewHealthCheck {
  final case class NewHealthCheckDefinition(
    http: Option[String] = None,
    header: Option[Map[String, String]] = None,
    method: Option[String] = None,
    body: Option[String] = None,
    tlsSkipVerify: Option[Boolean] = None,
    tcp: Option[String] = None,
    interval: Option[String] = None,
    timeout: Option[String] = None,
    deregisterCriticalServiceAfter: Option[String] = None
  )
}
