package consul4s.zio.json.model

import consul4s.model.ServiceKind
import consul4s.model.agent._
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import consul4s.model.health.NewHealthCheck
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Catalog { this: Agent with Health with Common =>
  private[zio] case class CatalogServiceRepr(
    ID: String,
    Node: String,
    Address: String,
    Datacenter: String,
    TaggedAddresses: Option[Map[String, String]],
    NodeMeta: Option[Map[String, String]],
    ServiceKind: ServiceKind,
    ServiceID: String,
    ServiceName: String,
    ServiceTags: Option[List[String]],
    ServiceAddress: String,
    ServiceTaggedAddresses: Option[Map[String, TaggedAddress]],
    ServiceWeights: Weights,
    ServiceMeta: Option[Map[String, String]],
    ServicePort: Int,
    ServiceEnableTagOverride: Boolean
  )

  implicit val catalogServiceCodec: JsonCodec[CatalogService] = ConverterMacros.derive[CatalogServiceRepr, CatalogService]

  private[zio] case class NewCatalogServiceRepr(
    Service: String,
    ID: Option[String] = None,
    Tags: Option[List[String]] = None,
    Address: Option[String] = None,
    TaggedAddresses: Option[Map[String, TaggedAddress]] = None,
    Meta: Option[Map[String, String]] = None,
    Port: Option[Int] = None,
    EnableTagOverride: Boolean = false,
    Weights: Option[Weights] = None
  )

  implicit val newCatalogServiceCodec: JsonCodec[NewCatalogService] = ConverterMacros.derive[NewCatalogServiceRepr, NewCatalogService]

  private[zio] case class NodeRepr(
    Node: String,
    Address: String,
    ID: Option[String],
    Datacenter: Option[String],
    TaggedAddresses: Option[Map[String, String]],
    Meta: Option[Map[String, String]]
  )

  implicit val nodeCodec: JsonCodec[Node] = ConverterMacros.derive[NodeRepr, Node]

  private[zio] case class NodeDeregistrationRepr(
    Node: String,
    Datacenter: Option[String] = None,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None
  )

  implicit val nodeDeregistrationCodec: JsonCodec[NodeDeregistration] = ConverterMacros.derive[NodeDeregistrationRepr, NodeDeregistration]

  private[zio] case class NodeRegistrationRepr(
    Node: String,
    Address: String,
    Service: Option[NewCatalogService] = None,
    Check: Option[NewHealthCheck] = None,
    Checks: Option[List[NewHealthCheck]] = None,
    ID: Option[String] = None,
    Datacenter: Option[String] = None,
    TaggedAddresses: Option[Map[String, String]] = None,
    NodeMeta: Option[Map[String, String]] = None,
    SkipNodeUpdate: Boolean = false
  )

  implicit val nodeRegistrationCodec: JsonCodec[NodeRegistration] = ConverterMacros.derive[NodeRegistrationRepr, NodeRegistration]

  private[zio] case class NodeServiceListInternalRepr(Node: Option[Node], Services: Option[List[Service]])

  implicit val nodeServiceListInternalCodec: JsonCodec[NodeServiceListInternal] =
    ConverterMacros.derive[NodeServiceListInternalRepr, NodeServiceListInternal]

  private[zio] case class NodeServiceMapRepr(Node: Node, Services: Map[String, Service])

  implicit val nodeServiceMapCodec: JsonCodec[NodeServiceMap] = ConverterMacros.derive[NodeServiceMapRepr, NodeServiceMap]
}
