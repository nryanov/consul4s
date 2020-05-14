package consul4s.model.health

final case class HealthCheckDefinition(
  HTTP: String,
  Header: Map[String, String],
  Method: String,
  Body: String,
  TLSSkipVerify: Boolean,
  TCP: String,
  IntervalDuration: String,
  TimeoutDuration: String,
  DeregisterCriticalServiceAfterDuration: String
)
