package consul4s.circe.model

import consul4s.model.Status
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import io.circe.Decoder.Result
import io.circe._

trait Health { this: Common =>
  implicit val healthCheckDefinitionDecoder: Decoder[HealthCheckDefinition] = new Decoder[HealthCheckDefinition] {
    override def apply(c: HCursor): Result[HealthCheckDefinition] = for {
      http <- c.downField("HTTP").as[String]
      header <- c.downField("Header").as[Map[String, String]]
      method <- c.downField("Method").as[String]
      body <- c.downField("Body").as[String]
      tlsSkipVerify <- c.downField("TLSSkipVerify").as[Boolean]
      tcp <- c.downField("TCP").as[String]
      intervalDuration <- c.downField("IntervalDuration").as[String]
      timeoutDuration <- c.downField("TimeoutDuration").as[String]
      deregisterCriticalServiceAfterDuration <- c.downField("DeregisterCriticalServiceAfterDuration").as[String]
    } yield HealthCheckDefinition(
      http,
      header,
      method,
      body,
      tlsSkipVerify,
      tcp,
      intervalDuration,
      timeoutDuration,
      deregisterCriticalServiceAfterDuration
    )
  }

  implicit val healthCheckDecoder: Decoder[HealthCheck] = new Decoder[HealthCheck] {
    override def apply(c: HCursor): Result[HealthCheck] = for {
      node <- c.downField("Node").as[String]
      checkId <- c.downField("CheckId").as[String]
      name <- c.downField("Name").as[String]
      status <- c.downField("Status").as[Status]
      notes <- c.downField("Notes").as[String]
      output <- c.downField("Output").as[String]
      serviceId <- c.downField("ServiceId").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      serviceTags <- c.downField("ServiceTags").as[List[String]]
      tType <- c.downField("Type").as[String]
      namespace <- c.downField("Namespace").as[Option[String]]
      definition <- c.downField("Definition").as[Option[HealthCheckDefinition]]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
    } yield HealthCheck(
      node,
      checkId,
      name,
      status,
      notes,
      output,
      serviceId,
      serviceName,
      serviceTags,
      tType,
      namespace,
      definition,
      createIndex,
      modifyIndex
    )
  }
}
