package consul4s.model.health

import consul4s.model.CheckStatus
import NewHealthCheck._

final case class NewHealthCheck(
  Node: String,
  Name: String,
  CheckID: Option[String] = None,
  Status: Option[CheckStatus] = None,
  Notes: Option[String] = None,
  Output: Option[String] = None,
  ServiceID: Option[String] = None,
  ServiceName: Option[String] = None,
  ServiceTags: Option[List[String]] = None,
  Type: Option[String] = None,
  Definition: Option[NewHealthCheckDefinition] = None
)

object NewHealthCheck {
  final case class NewHealthCheckDefinition(
    HTTP: Option[String] = None,
    Header: Option[Map[String, String]] = None,
    Method: Option[String] = None,
    Body: Option[String] = None,
    TLSSkipVerify: Option[Boolean] = None,
    TCP: Option[String] = None,
    Interval: Option[String] = None,
    Timeout: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )
}
