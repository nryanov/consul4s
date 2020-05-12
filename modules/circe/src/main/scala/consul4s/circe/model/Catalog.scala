package consul4s.circe.model

import consul4s.model.agent.{AgentCheck, AgentService, AgentServiceConnectProxyConfig}
import consul4s.model.catalog._
import consul4s.model.health.HealthCheck
import io.circe.Decoder.Result
import io.circe._

trait Catalog { this: Agent with Health =>
  implicit val serviceAddressDecoder: Decoder[ServiceAddress] = new Decoder[ServiceAddress] {
    override def apply(c: HCursor): Result[ServiceAddress] = for {
      address <- c.downField("Address").as[String]
      port <- c.downField("Port").as[Int]
    } yield ServiceAddress(address, port)
  }

  implicit val weightsDecoder: Decoder[Weights] = new Decoder[Weights] {
    override def apply(c: HCursor): Result[Weights] = for {
      passing <- c.downField("Passing").as[Int]
      warning <- c.downField("Warning").as[Int]
    } yield Weights(passing, warning)
  }

  implicit val catalogDeregistrationDecoder: Decoder[CatalogDeregistration] = new Decoder[CatalogDeregistration] {
    override def apply(c: HCursor): Result[CatalogDeregistration] = for {
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[Option[String]]
      datacenter <- c.downField("Datacenter").as[String]
      serviceId <- c.downField("ServiceId").as[String]
      checkId <- c.downField("CheckId").as[String]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield CatalogDeregistration(node, address, datacenter, serviceId, checkId, namespace)
  }

  implicit val catalogNodeDecoder: Decoder[CatalogNode] = new Decoder[CatalogNode] {
    override def apply(c: HCursor): Result[CatalogNode] = for {
      node <- c.downField("Node").as[Option[Node]]
      services <- c.downField("Services").as[Map[String, AgentService]]
    } yield CatalogNode(node, services)
  }

  implicit val catalogNodeServiceListDecoder: Decoder[CatalogNodeServiceList] = new Decoder[CatalogNodeServiceList] {
    override def apply(c: HCursor): Result[CatalogNodeServiceList] = for {
      node <- c.downField("Node").as[Option[Node]]
      services <- c.downField("Services").as[List[AgentService]]
    } yield CatalogNodeServiceList(node, services)
  }

  implicit val catalogRegistrationDecoder: Decoder[CatalogRegistration] = new Decoder[CatalogRegistration] {
    override def apply(c: HCursor): Result[CatalogRegistration] = for {
      id <- c.downField("ID").as[String]
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Map[String, String]]
      nodeMeta <- c.downField("NodeMeta").as[Map[String, String]]
      datacenter <- c.downField("Datacenter").as[String]
      service <- c.downField("Service").as[Option[AgentService]]
      check <- c.downField("Check").as[Option[AgentCheck]]
      checks <- c.downField("Checks").as[List[HealthCheck]]
      skipNodeUpdate <- c.downField("skipNodeUpdate").as[Boolean]
    } yield CatalogRegistration(id, node, address, taggedAddresses, nodeMeta, datacenter, service, check, checks, skipNodeUpdate)
  }

  implicit val catalogServiceDecoder: Decoder[CatalogService] = new Decoder[CatalogService] {
    override def apply(c: HCursor): Result[CatalogService] = for {
      id <- c.downField("ID").as[String]
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[String]
      datacenter <- c.downField("Datacenter").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Map[String, String]]
      nodeMeta <- c.downField("NodeMeta").as[Map[String, String]]
      serviceId <- c.downField("ServiceId").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      serviceAddress <- c.downField("ServiceAddress").as[String]
      serviceTaggedAddresses <- c.downField("ServiceTaggedAddresses").as[Map[String, ServiceAddress]]
      serviceTags <- c.downField("ServiceTags").as[List[String]]
      serviceMeta <- c.downField("ServiceMeta").as[Map[String, String]]
      servicePort <- c.downField("ServicePort").as[Int]
      serviceWeights <- c.downField("ServiceWeights").as[Weights]
      serviceEnableTagOverride <- c.downField("ServiceEnableTagOverride").as[Boolean]
      serviceProxy <- c.downField("ServiceProxy").as[Option[AgentServiceConnectProxyConfig]]
      createIndex <- c.downField("XreateIndex").as[Long]
      checks <- c.downField("Checks").as[List[HealthCheck]]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield CatalogService(
      id,
      node,
      address,
      datacenter,
      taggedAddresses,
      nodeMeta,
      serviceId,
      serviceName,
      serviceAddress,
      serviceTaggedAddresses,
      serviceTags,
      serviceMeta,
      servicePort,
      serviceWeights,
      serviceEnableTagOverride,
      serviceProxy,
      createIndex,
      checks,
      modifyIndex,
      namespace
    )
  }

  implicit val nodeDecoder: Decoder[Node] = new Decoder[Node] {
    override def apply(c: HCursor): Result[Node] = for {
      id <- c.downField("ID").as[String]
      node <- c.downField("Node").as[String]
      address <- c.downField("Address").as[String]
      datacenter <- c.downField("Datacanter").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Map[String, String]]
      meta <- c.downField("Meta").as[Map[String, String]]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
    } yield Node(id, node, address, datacenter, taggedAddresses, meta, createIndex, modifyIndex)
  }
}
