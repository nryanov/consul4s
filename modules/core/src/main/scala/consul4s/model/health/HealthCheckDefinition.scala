package consul4s.model.health

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
