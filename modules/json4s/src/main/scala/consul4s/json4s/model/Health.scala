package consul4s.json4s.model

import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import consul4s.model.health._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Health {

  val healthCheckFormat = FieldSerializer[HealthCheck](
    Map(),
    renameFrom("Node", "node")
      .orElse(renameFrom("CheckID", "checkID"))
      .orElse(renameFrom("Name", "name"))
      .orElse(renameFrom("Status", "status"))
      .orElse(renameFrom("Notes", "notes"))
      .orElse(renameFrom("Output", "output"))
      .orElse(renameFrom("ServiceID", "serviceID"))
      .orElse(renameFrom("ServiceName", "serviceName"))
      .orElse(renameFrom("ServiceTags", "serviceTags"))
      .orElse(renameFrom("Type", "tType"))
      .orElse(renameFrom("Definition", "definition"))
      .orElse(renameFrom("CreateIndex", "createIndex"))
      .orElse(renameFrom("ModifyIndex", "modifyIndex"))
  )

  val healthCheckDefinitionFormat = FieldSerializer[HealthCheckDefinition](
    Map(),
    renameFrom("HTTP", "http")
      .orElse(renameFrom("Header", "header"))
      .orElse(renameFrom("Method", "method"))
      .orElse(renameFrom("Body", "body"))
      .orElse(renameFrom("TLSSkipVerify", "tlsSkipVerify"))
      .orElse(renameFrom("TCP", "tcp"))
      .orElse(renameFrom("Interval", "interval"))
      .orElse(renameFrom("Timeout", "timeout"))
      .orElse(renameFrom("DeregisterCriticalServiceAfter", "deregisterCriticalServiceAfter"))
  )

  val newHealthCheckFormat = FieldSerializer[NewHealthCheck](
    renameTo("node", "Node")
      .orElse(renameTo("name", "Name"))
      .orElse(renameTo("checkId", "CheckID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("output", "Output"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("serviceName", "ServiceName"))
      .orElse(renameTo("serviceTags", "ServiceTags"))
      .orElse(renameTo("type", "Type"))
      .orElse(renameTo("definition", "Definition")),
    Map()
  )

  val newHealthCheckDefinitionFormat = FieldSerializer[NewHealthCheckDefinition](
    renameTo("http", "HTTP")
      .orElse(renameTo("header", "Header"))
      .orElse(renameTo("method", "Method"))
      .orElse(renameTo("body", "Body"))
      .orElse(renameTo("tlsSkipVerify", "TLSSkipVerify"))
      .orElse(renameTo("tcp", "TCP"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("timeout", "Timeout"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val serviceEntryFormat = FieldSerializer[ServiceEntry](
    Map(),
    renameFrom("Node", "node").orElse(renameFrom("Service", "service")).orElse(renameFrom("Checks", "checks"))
  )

  val healthAllFieldSerializers =
    List(healthCheckFormat, healthCheckDefinitionFormat, newHealthCheckFormat, newHealthCheckDefinitionFormat, serviceEntryFormat)
}
