package consul4s.model.health

final case class HealthCheckDefinition(
  HTTP: String,
  Header: Option[Map[String, String]],
  Method: String,
  Body: String,
  TLSSkipVerify: Boolean,
  TCP: String,
  Interval: String,
  Timeout: String,
  DeregisterCriticalServiceAfter: String
)
