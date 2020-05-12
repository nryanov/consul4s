package consul4s.json4s.model

import consul4s.model.Status
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import org.json4s.{CustomSerializer, JObject}

trait Health {
  val healthCheckDefinitionSerializer = new CustomSerializer[HealthCheckDefinition](
    implicit format =>
      (
        {
          case json: JObject =>
            HealthCheckDefinition(
              (json \ "HTTP").extract[String],
              (json \ "Header").extract[Map[String, String]],
              (json \ "Method").extract[String],
              (json \ "Body").extract[String],
              (json \ "TLSSkipVerify").extract[Boolean],
              (json \ "TCP").extract[String],
              (json \ "IntervalDuration").extract[String],
              (json \ "TimeoutDuration").extract[String],
              (json \ "DeregisterCriticalServiceAfterDuration").extract[String]
            )
        }, {
          case _: HealthCheckDefinition => JObject()
        }
      )
  )

  val healthCheckSerializer = new CustomSerializer[HealthCheck](
    implicit format =>
      (
        {
          case json: JObject =>
            HealthCheck(
              (json \ "Node").extract[String],
              (json \ "CheckId").extract[String],
              (json \ "Name").extract[String],
              (json \ "Status").extract[Status],
              (json \ "Notes").extract[String],
              (json \ "Output").extract[String],
              (json \ "ServiceId").extract[String],
              (json \ "ServiceName").extract[String],
              (json \ "ServiceTags").extract[List[String]],
              (json \ "Type").extract[String],
              (json \ "Namespace").extract[Option[String]],
              (json \ "Definition").extract[Option[HealthCheckDefinition]],
              (json \ "CreateIndex").extract[Long],
              (json \ "ModifyIndex").extract[Long]
            )
        }, {
          case _: HealthCheck => JObject()
        }
      )
  )
}
