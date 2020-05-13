package consul4s.sprayJson.model

import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
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
    "IntervalDuration",
    "TimeoutDuration",
    "DeregisterCriticalServiceAfterDuration"
  )

  implicit val healthCheckFormat: RootJsonFormat[HealthCheck] = jsonFormat(
    HealthCheck.apply,
    "Node",
    "CheckId",
    "Name",
    "Status",
    "Notes",
    "Output",
    "ServiceId",
    "ServiceName",
    "ServiceTags",
    "Type",
    "Namespace",
    "Definition",
    "CreateIndex",
    "ModifyIndex"
  )

  implicit val serviceEntryFormat: RootJsonFormat[ServiceEntry] = jsonFormat(
    ServiceEntry.apply,
    "Node",
    "Service",
    "Checks"
  )
}
