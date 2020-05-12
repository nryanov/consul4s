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
      node <- c.downField("node").as[String]
      checkId <- c.downField("checkId").as[String]
      name <- c.downField("name").as[String]
      status <- c.downField("status").as[Status]
      notes <- c.downField("notes").as[String]
      output <- c.downField("output").as[String]
      serviceId <- c.downField("serviceId").as[String]
      serviceName <- c.downField("serviceName").as[String]
      serviceTags <- c.downField("serviceTags").as[List[String]]
      tType <- c.downField("type").as[String]
      namespace <- c.downField("namespace").as[Option[String]]
      definition <- c.downField("definition").as[Option[HealthCheckDefinition]]
      createIndex <- c.downField("createIndex").as[Long]
      modifyIndex <- c.downField("modifyIndex").as[Long]
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
