package consul4s.json4s.model

import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import consul4s.model.health._
import org.json4s.FieldSerializer

trait Health {

  val healthCheckFormat: FieldSerializer[HealthCheck] = FieldSerializer[HealthCheck]()

  val healthCheckDefinitionFormat: FieldSerializer[HealthCheckDefinition] = FieldSerializer[HealthCheckDefinition]()

  val newHealthCheckFormat: FieldSerializer[NewHealthCheck] = FieldSerializer[NewHealthCheck]()

  val newHealthCheckDefinitionFormat: FieldSerializer[NewHealthCheckDefinition] = FieldSerializer[NewHealthCheckDefinition]()

  val serviceEntryFormat: FieldSerializer[ServiceEntry] = FieldSerializer[ServiceEntry]()

  val healthAllFieldSerializers =
    List(healthCheckFormat, healthCheckDefinitionFormat, newHealthCheckFormat, newHealthCheckDefinitionFormat, serviceEntryFormat)
}
