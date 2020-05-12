package consul4s.model.health

final case class HealthCheckDefinition(
  http: String,
  header: Map[String, String],
  method: String,
  body: String,
  tlsSkipVerify: Boolean,
  tcp: String,
  intervalDuration: String,
  timeoutDuration: String,
  deregisterCriticalServiceAfterDuration: String
)
