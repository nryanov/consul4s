package consul4s.circe.model

import consul4s.model.CheckStatus
import consul4s.model.agent.Service
import consul4s.model.catalog.Node
import consul4s.model.health.{HealthCheck, NewHealthCheck, ServiceEntry}
import consul4s.model.health.HealthCheck._
import consul4s.model.health.NewHealthCheck._
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait Health { this: Common with Agent with Catalog =>
  implicit val healthCheckDefinitionDecoder: Decoder[HealthCheckDefinition] = new Decoder[HealthCheckDefinition] {
    override def apply(c: HCursor): Result[HealthCheckDefinition] = for {
      http <- c.downField("HTTP").as[Option[String]]
      header <- c.downField("Header").as[Option[Map[String, String]]]
      method <- c.downField("Method").as[Option[String]]
      body <- c.downField("Body").as[Option[String]]
      tlsSkipVerify <- c.downField("TLSSkipVerify").as[Option[Boolean]]
      tcp <- c.downField("TCP").as[Option[String]]
      interval <- c.downField("Interval").as[Option[String]]
      timeout <- c.downField("Timeout").as[Option[String]]
      deregisterCriticalServiceAfter <- c.downField("DeregisterCriticalServiceAfter").as[Option[String]]
    } yield HealthCheckDefinition(
      http,
      header,
      method,
      body,
      tlsSkipVerify,
      tcp,
      interval,
      timeout,
      deregisterCriticalServiceAfter
    )
  }

  implicit val healthCheckDecoder: Decoder[HealthCheck] = new Decoder[HealthCheck] {
    override def apply(c: HCursor): Result[HealthCheck] = for {
      node <- c.downField("Node").as[String]
      checkID <- c.downField("CheckID").as[String]
      name <- c.downField("Name").as[String]
      status <- c.downField("Status").as[CheckStatus]
      notes <- c.downField("Notes").as[String]
      output <- c.downField("Output").as[String]
      serviceID <- c.downField("ServiceID").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      serviceTags <- c.downField("ServiceTags").as[Option[List[String]]]
      tType <- c.downField("Type").as[String]
      definition <- c.downField("Definition").as[HealthCheckDefinition]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
    } yield HealthCheck(
      node,
      checkID,
      name,
      status,
      notes,
      output,
      serviceID,
      serviceName,
      serviceTags,
      tType,
      definition,
      createIndex,
      modifyIndex
    )
  }

  implicit val newHealthCheckDefinitionEncoder: Encoder[NewHealthCheckDefinition] = new Encoder[NewHealthCheckDefinition] {
    override def apply(a: NewHealthCheckDefinition): Json = Json.obj(
      ("HTTP", a.http.asJson),
      ("Header", a.header.asJson),
      ("Method", a.method.asJson),
      ("Body", a.body.asJson),
      ("TLSSkipVerify", a.tlsSkipVerify.asJson),
      ("TCP", a.tcp.asJson),
      ("Interval", a.interval.asJson),
      ("Timeout", a.timeout.asJson),
      ("DeregisterCriticalServiceAfter", a.deregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val newHealthCheckEncoder: Encoder[NewHealthCheck] = new Encoder[NewHealthCheck] {
    override def apply(a: NewHealthCheck): Json = Json.obj(
      ("Node", a.node.asJson),
      ("Name", a.name.asJson),
      ("CheckID", a.checkId.asJson),
      ("Status", a.status.asJson),
      ("Notes", a.notes.asJson),
      ("Output", a.output.asJson),
      ("ServiceID", a.serviceId.asJson),
      ("ServiceName", a.serviceName.asJson),
      ("ServiceTags", a.serviceTags.asJson),
      ("Type", a.`type`.asJson),
      ("Definition", a.definition.asJson)
    )
  }

  implicit val serviceEntryDecoder: Decoder[ServiceEntry] = new Decoder[ServiceEntry] {
    override def apply(c: HCursor): Result[ServiceEntry] = for {
      node <- c.downField("Node").as[Node]
      service <- c.downField("Service").as[Service]
      checks <- c.downField("Checks").as[List[HealthCheck]]
    } yield ServiceEntry(node, service, checks)
  }
}
