package consul4s.ziojson.model

import consul4s.model.health.{HealthCheck, NewHealthCheck, ServiceEntry}
import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Health { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionEncoder: JsonEncoder[HealthCheckDefinition] = DeriveJsonEncoder.gen[HealthCheckDefinition]
  implicit val healthCheckEncoder: JsonEncoder[HealthCheck] = DeriveJsonEncoder.gen[HealthCheck]
  implicit val newHealthCheckDefinitionEncoder: JsonEncoder[NewHealthCheckDefinition] = DeriveJsonEncoder.gen[NewHealthCheckDefinition]
  implicit val newHealthCheckEncoder: JsonEncoder[NewHealthCheck] = DeriveJsonEncoder.gen[NewHealthCheck]
  implicit val serviceEntryEncoder: JsonEncoder[ServiceEntry] = DeriveJsonEncoder.gen[ServiceEntry]

  implicit val healthCheckDefinitionDecoder: JsonDecoder[HealthCheckDefinition] = DeriveJsonDecoder.gen[HealthCheckDefinition]
  implicit val healthCheckDecoder: JsonDecoder[HealthCheck] = DeriveJsonDecoder.gen[HealthCheck]
  implicit val newHealthCheckDefinitionDecoder: JsonDecoder[NewHealthCheckDefinition] = DeriveJsonDecoder.gen[NewHealthCheckDefinition]
  implicit val newHealthCheckDecoder: JsonDecoder[NewHealthCheck] = DeriveJsonDecoder.gen[NewHealthCheck]
  implicit val serviceEntryDecoder: JsonDecoder[ServiceEntry] = DeriveJsonDecoder.gen[ServiceEntry]
}
