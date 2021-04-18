package consul4s.sprayJson.model

import consul4s.model.health._
import consul4s.model.health.HealthCheck._
import consul4s.model.health.NewHealthCheck._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Health extends DefaultJsonProtocol { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionFormat: RootJsonFormat[HealthCheckDefinition] = jsonFormat9(HealthCheckDefinition.apply)

  implicit val healthCheckFormat: RootJsonFormat[HealthCheck] = jsonFormat13(HealthCheck.apply)

  implicit val newHealthCheckDefinitionFormat: RootJsonFormat[NewHealthCheckDefinition] = jsonFormat9(NewHealthCheckDefinition.apply)

  implicit val newHealthCheckFormat: RootJsonFormat[NewHealthCheck] = jsonFormat11(NewHealthCheck.apply)

  implicit val serviceEntryFormat: RootJsonFormat[ServiceEntry] = jsonFormat3(ServiceEntry.apply)

}
