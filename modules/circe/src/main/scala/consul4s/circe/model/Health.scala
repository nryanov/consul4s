package consul4s.circe.model

import consul4s.model.health.{HealthCheck, NewHealthCheck, ServiceEntry}
import consul4s.model.health.HealthCheck._
import consul4s.model.health.NewHealthCheck._
import io.circe._
import io.circe.generic.semiauto._

trait Health { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionDecoder: Decoder[HealthCheckDefinition] = deriveDecoder[HealthCheckDefinition]
  implicit val healthCheckDecoder: Decoder[HealthCheck] = deriveDecoder[HealthCheck]

  implicit val newHealthCheckDefinitionEncoder: Encoder[NewHealthCheckDefinition] = deriveEncoder[NewHealthCheckDefinition]
  implicit val newHealthCheckEncoder: Encoder[NewHealthCheck] = deriveEncoder[NewHealthCheck]

  implicit val serviceEntryDecoder: Decoder[ServiceEntry] = deriveDecoder[ServiceEntry]
}
