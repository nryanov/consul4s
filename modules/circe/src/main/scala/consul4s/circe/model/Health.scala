package consul4s.circe.model

import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
import io.circe._
import io.circe.generic.semiauto._

trait Health { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionEncoder: Encoder[HealthCheckDefinition] = deriveEncoder[HealthCheckDefinition]
  implicit val healthCheckDefinitionDecoder: Decoder[HealthCheckDefinition] = deriveDecoder[HealthCheckDefinition]

  implicit val healthCheckEncoder: Encoder[HealthCheck] = deriveEncoder[HealthCheck]
  implicit val healthCheckDecoder: Decoder[HealthCheck] = deriveDecoder[HealthCheck]

  implicit val serviceEntryEncoder: Encoder[ServiceEntry] = deriveEncoder[ServiceEntry]
  implicit val serviceEntryDecoder: Decoder[ServiceEntry] = deriveDecoder[ServiceEntry]
}
