package consul4s.zio.json.model

import consul4s.model.CheckStatus
import consul4s.model.agent.Service
import consul4s.model.catalog.Node
import consul4s.model.health.{HealthCheck, NewHealthCheck, ServiceEntry}
import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Health { this: Common with Agent with Catalog =>

  private[zio] case class HealthCheckDefinitionRepr(
    HTTP: Option[String],
    Header: Option[Map[String, String]],
    Method: Option[String],
    Body: Option[String],
    TLSSkipVerify: Option[Boolean],
    TCP: Option[String],
    Interval: Option[String],
    Timeout: Option[String],
    DeregisterCriticalServiceAfter: Option[String]
  )

  implicit val healthCheckDefinitionCodec: JsonCodec[HealthCheckDefinition] =
    ConverterMacros.derive[HealthCheckDefinitionRepr, HealthCheckDefinition]

  private[zio] case class HealthCheckRepr(
    Node: String,
    CheckID: String,
    Name: String,
    Status: CheckStatus,
    Notes: String,
    Output: String,
    ServiceID: String,
    ServiceName: String,
    ServiceTags: Option[List[String]],
    Type: String,
    Definition: HealthCheckDefinition,
    CreateIndex: Long,
    ModifyIndex: Long
  )

  implicit val healthCheckCodec: JsonCodec[HealthCheck] = ConverterMacros.derive[HealthCheckRepr, HealthCheck]

  private[zio] case class NewHealthCheckDefinitionRepr(
    HTTP: Option[String] = None,
    Header: Option[Map[String, String]] = None,
    Method: Option[String] = None,
    Body: Option[String] = None,
    TLSSkipVerify: Option[Boolean] = None,
    TCP: Option[String] = None,
    Interval: Option[String] = None,
    Timeout: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val newHealthCheckDefinitionCodec: JsonCodec[NewHealthCheckDefinition] =
    ConverterMacros.derive[NewHealthCheckDefinitionRepr, NewHealthCheckDefinition]

  private[zio] case class NewHealthCheckRepr(
    Node: String,
    Name: String,
    CheckID: Option[String] = None,
    Status: Option[CheckStatus] = None,
    Notes: Option[String] = None,
    Output: Option[String] = None,
    ServiceID: Option[String] = None,
    ServiceName: Option[String] = None,
    ServiceTags: Option[List[String]] = None,
    Type: Option[String] = None,
    Definition: Option[NewHealthCheckDefinition] = None
  )

  implicit val newHealthCheckCodec: JsonCodec[NewHealthCheck] = ConverterMacros.derive[NewHealthCheckRepr, NewHealthCheck]

  private[zio] case class ServiceEntryRepr(Node: Node, Service: Service, Checks: List[HealthCheck])

  implicit val serviceEntryCodec: JsonCodec[ServiceEntry] = ConverterMacros.derive[ServiceEntryRepr, ServiceEntry]
}
