package consul4s.json4s.model

import consul4s.model.agent.{AgentCheck, AgentService, AgentServiceConnectProxyConfig}
import consul4s.model.catalog._
import consul4s.model.health.HealthCheck
import org.json4s.{CustomSerializer, JObject}

trait Catalog {
  val serviceAddressSerializer = new CustomSerializer[ServiceAddress](
    implicit format =>
      (
        {
          case json: JObject =>
            ServiceAddress(
              (json \ "Address").extract[String],
              (json \ "Port").extract[Int]
            )
        }, {
          case _: ServiceAddress => JObject()
        }
      )
  )

  val weightsSerializer = new CustomSerializer[Weights](
    implicit format =>
      (
        {
          case json: JObject =>
            Weights(
              (json \ "Passing").extract[Int],
              (json \ "Warning").extract[Int]
            )
        }, {
          case _: Weights => JObject()
        }
      )
  )

  val catalogDeregistrationSerializer = new CustomSerializer[CatalogDeregistration](
    implicit format =>
      (
        {
          case json: JObject =>
            CatalogDeregistration(
              (json \ "Node").extract[String],
              (json \ "Address").extract[Option[String]],
              (json \ "Datacenter").extract[String],
              (json \ "ServiceId").extract[String],
              (json \ "CheckId").extract[String],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _: CatalogDeregistration => JObject()
        }
      )
  )

  val catalogNodeSerializer = new CustomSerializer[CatalogNode](
    implicit format =>
      (
        {
          case json: JObject =>
            CatalogNode(
              (json \ "Node").extract[Option[Node]],
              (json \ "Services").extract[Map[String, AgentService]]
            )
        }, {
          case _: CatalogNode => JObject()
        }
      )
  )

  val catalogNodeServiceListSerializer = new CustomSerializer[CatalogNodeServiceList](
    implicit format =>
      (
        {
          case json: JObject =>
            CatalogNodeServiceList(
              (json \ "Node").extract[Option[Node]],
              (json \ "Services").extract[List[AgentService]]
            )
        }, {
          case _: CatalogNodeServiceList => JObject()
        }
      )
  )

  val catalogRegistrationSerializer = new CustomSerializer[CatalogRegistration](
    implicit format =>
      (
        {
          case json: JObject =>
            CatalogRegistration(
              (json \ "ID").extract[String],
              (json \ "Node").extract[String],
              (json \ "Address").extract[String],
              (json \ "TaggedAddresses").extract[Map[String, String]],
              (json \ "NodeMeta").extract[Map[String, String]],
              (json \ "Datacenter").extract[String],
              (json \ "Service").extract[Option[AgentService]],
              (json \ "Check").extract[Option[AgentCheck]],
              (json \ "Checks").extract[List[HealthCheck]],
              (json \ "SkipNodeUpdate").extract[Boolean]
            )
        }, {
          case _: CatalogRegistration => JObject()
        }
      )
  )

  val catalogServiceSerializer = new CustomSerializer[CatalogService](
    implicit format =>
      (
        {
          case json: JObject =>
            CatalogService(
              (json \ "ID").extract[String],
              (json \ "Node").extract[String],
              (json \ "Address").extract[String],
              (json \ "Datacenter").extract[String],
              (json \ "TaggedAddresses").extract[Map[String, String]],
              (json \ "NodeMeta").extract[Map[String, String]],
              (json \ "ServiceId").extract[String],
              (json \ "ServiceName").extract[String],
              (json \ "ServiceAddress").extract[String],
              (json \ "ServiceTaggedAddresses").extract[Map[String, ServiceAddress]],
              (json \ "ServiceTags").extract[List[String]],
              (json \ "ServiceMeta").extract[Map[String, String]],
              (json \ "ServicePort").extract[Int],
              (json \ "ServiceWeights").extract[Weights],
              (json \ "ServiceEnableTagOverride").extract[Boolean],
              (json \ "ServiceProxy").extract[Option[AgentServiceConnectProxyConfig]],
              (json \ "XreateIndex").extract[Long],
              (json \ "Checks").extract[List[HealthCheck]],
              (json \ "ModifyIndex").extract[Long],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _: CatalogService => JObject()
        }
      )
  )

  val nodeSerializer = new CustomSerializer[Node](
    implicit format =>
      (
        {
          case json: JObject =>
            Node(
              (json \ "ID").extract[String],
              (json \ "Node").extract[String],
              (json \ "Address").extract[String],
              (json \ "Datacanter").extract[String],
              (json \ "TaggedAddresses").extract[Map[String, String]],
              (json \ "Meta").extract[Map[String, String]],
              (json \ "CreateIndex").extract[Long],
              (json \ "ModifyIndex").extract[Long]
            )
        }, {
          case _: Node => JObject()
        }
      )
  )
}
