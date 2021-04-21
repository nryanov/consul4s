package consul4s.ziojson.model

import consul4s.model.health.{HealthCheck, NewHealthCheck, ServiceEntry}
import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Health { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionCodec: JsonCodec[HealthCheckDefinition] = DeriveJsonCodec.gen[HealthCheckDefinition]
  implicit val healthCheckCodec: JsonCodec[HealthCheck] = DeriveJsonCodec.gen[HealthCheck]
  implicit val newHealthCheckDefinitionCodec: JsonCodec[NewHealthCheckDefinition] = DeriveJsonCodec.gen[NewHealthCheckDefinition]
  implicit val newHealthCheckCodec: JsonCodec[NewHealthCheck] = DeriveJsonCodec.gen[NewHealthCheck]
  implicit val serviceEntryCodec: JsonCodec[ServiceEntry] = DeriveJsonCodec.gen[ServiceEntry]
}
