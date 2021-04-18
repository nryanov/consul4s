package consul4s.circe.model

import consul4s.model.ServiceKind
import consul4s.model.agent.{Service, TaggedAddress, Weights}
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceInfoDecoder: Decoder[CatalogService] = new Decoder[CatalogService] {
    override def apply(c: HCursor): Result[CatalogService] = for {
      id <- c.downField("ID").as[String]
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[String]
      datacenter <- c.downField("Datacenter").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Option[Map[String, String]]]
      nodeMeta <- c.downField("NodeMeta").as[Option[Map[String, String]]]
      serviceKind <- c.downField("ServiceKind").as[ServiceKind]
      serviceID <- c.downField("ServiceID").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      serviceTags <- c.downField("ServiceTags").as[Option[List[String]]]
      serviceAddress <- c.downField("ServiceAddress").as[String]
      serviceTaggedAddresses <- c.downField("ServiceTaggedAddresses").as[Option[Map[String, TaggedAddress]]]
      serviceWeights <- c.downField("ServiceWeights").as[Weights]
      serviceMeta <- c.downField("ServiceMeta").as[Option[Map[String, String]]]
      servicePort <- c.downField("ServicePort").as[Int]
      serviceEnableTagOverride <- c.downField("ServiceEnableTagOverride").as[Boolean]
    } yield CatalogService(
      id,
      node,
      address,
      datacenter,
      taggedAddresses,
      nodeMeta,
      serviceKind,
      serviceID,
      serviceName,
      serviceTags,
      serviceAddress,
      serviceTaggedAddresses,
      serviceWeights,
      serviceMeta,
      servicePort,
      serviceEnableTagOverride
    )
  }

  implicit val newCatalogServiceInfoEncoder: Encoder[NewCatalogService] = new Encoder[NewCatalogService] {
    override def apply(a: NewCatalogService): Json = Json.obj(
      ("Service", a.Service.asJson),
      ("ID", a.ID.asJson),
      ("Tags", a.Tags.asJson),
      ("Address", a.Address.asJson),
      ("TaggedAddresses", a.TaggedAddresses.asJson),
      ("Meta", a.Meta.asJson),
      ("Port", a.Port.asJson),
      ("EnableTagOverride", a.EnableTagOverride.asJson),
      ("Weights", a.Weights.asJson)
    )
  }

  implicit val nodeDecoder: Decoder[Node] = new Decoder[Node] {
    override def apply(c: HCursor): Result[Node] = for {
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[String]
      id <- c.downField("ID").as[Option[String]]
      datacenter <- c.downField("Datacenter").as[Option[String]]
      taggedAddresses <- c.downField("TaggedAddresses").as[Option[Map[String, String]]]
      meta <- c.downField("Meta").as[Option[Map[String, String]]]
    } yield Node(
      node,
      address,
      id,
      datacenter,
      taggedAddresses,
      meta
    )
  }

  implicit val entityDeregistrationEncoder: Encoder[NodeDeregistration] = new Encoder[NodeDeregistration] {
    override def apply(a: NodeDeregistration): Json = Json.obj(
      ("Node", a.Node.asJson),
      ("Datacenter", a.Datacenter.asJson),
      ("CheckID", a.CheckID.asJson),
      ("ServiceID", a.ServiceID.asJson)
    )
  }

  implicit val entityRegistrationEncoder: Encoder[NodeRegistration] = new Encoder[NodeRegistration] {
    override def apply(a: NodeRegistration): Json = Json.obj(
      ("Node", a.Node.asJson),
      ("Address", a.Address.asJson),
      ("Service", a.Service.asJson),
      ("Check", a.Check.asJson),
      ("Checks", a.Checks.asJson),
      ("ID", a.ID.asJson),
      ("Datacenter", a.Datacenter.asJson),
      ("TaggedAddresses", a.TaggedAddresses.asJson),
      ("NodeMeta", a.NodeMeta.asJson),
      ("SkipNodeUpdate", a.SkipNodeUpdate.asJson)
    )
  }

  implicit val nodeServiceListInternalDecoder: Decoder[NodeServiceListInternal] = new Decoder[NodeServiceListInternal] {
    override def apply(c: HCursor): Result[NodeServiceListInternal] = for {
      node <- c.downField("Node").as[Option[Node]]
      services <- c.downField("Services").as[Option[List[Service]]]
    } yield NodeServiceListInternal(node, services)
  }

  implicit val nodeServiceMapDecoder: Decoder[NodeServiceMap] = new Decoder[NodeServiceMap] {
    override def apply(c: HCursor): Result[NodeServiceMap] = for {
      node <- c.downField("Node").as[Node]
      services <- c.downField("Services").as[Map[String, Service]]
    } yield NodeServiceMap(node, services)
  }
}
