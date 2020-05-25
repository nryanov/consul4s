package consul4s.sprayJson.model

import consul4s.model.health._
import consul4s.model.health.HealthCheck._
import consul4s.model.health.NewHealthCheck._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Health extends DefaultJsonProtocol { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionFormat: RootJsonFormat[HealthCheckDefinition] = jsonFormat(
    HealthCheckDefinition.apply,
    "HTTP",
    "Header",
    "Method",
    "Body",
    "TLSSkipVerify",
    "TCP",
    "Interval",
    "Timeout",
    "DeregisterCriticalServiceAfter"
  )

  implicit val healthCheckFormat: RootJsonFormat[HealthCheck] = jsonFormat(
    HealthCheck.apply,
    "Node",
    "CheckID",
    "Name",
    "Status",
    "Notes",
    "Output",
    "ServiceID",
    "ServiceName",
    "ServiceTags",
    "Type",
    "Definition",
    "CreateIndex",
    "ModifyIndex"
  )

  implicit val newHealthCheckDefinitionFormat: RootJsonFormat[NewHealthCheckDefinition] = jsonFormat(
    NewHealthCheckDefinition.apply,
    "HTTP",
    "Header",
    "Method",
    "Body",
    "TLSSkipVerify",
    "TCP",
    "Interval",
    "Timeout",
    "DeregisterCriticalServiceAfter"
  )

  implicit val newHealthCheckFormat: RootJsonFormat[NewHealthCheck] = jsonFormat(
    NewHealthCheck.apply,
    "Node",
    "Name",
    "CheckID",
    "Status",
    "Notes",
    "Output",
    "ServiceID",
    "ServiceName",
    "ServiceTags",
    "Type",
    "Definition"
  )

  implicit val serviceEntryFormat: RootJsonFormat[ServiceEntry] = jsonFormat(ServiceEntry.apply, "Node", "Service", "Checks")

}
