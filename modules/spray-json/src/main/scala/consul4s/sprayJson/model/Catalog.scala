package consul4s.sprayJson.model

import consul4s.model.catalog._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Catalog extends DefaultJsonProtocol { this: Health with Agent =>
  implicit val serviceAddressFormat: RootJsonFormat[ServiceAddress] = jsonFormat(ServiceAddress.apply, "Address", "Port")

  implicit val weightsFormat: RootJsonFormat[Weights] = jsonFormat(Weights.apply, "Passing", "Warning")

  implicit val catalogDeregistrationFormat: RootJsonFormat[CatalogDeregistration] =
    jsonFormat(CatalogDeregistration.apply, "Node", "Address", "Datacenter", "ServiceId", "CheckId", "Namespace")

  implicit val catalogNodeFormat: RootJsonFormat[CatalogNode] = jsonFormat(CatalogNode.apply, "Node", "Services")

  implicit val catalogNodeServiceListFormat: RootJsonFormat[CatalogNodeServiceList] =
    jsonFormat(CatalogNodeServiceList.apply, "Node", "Services")

  implicit val catalogRegistrationFormat: RootJsonFormat[CatalogRegistration] = jsonFormat(
    CatalogRegistration.apply,
    "ID",
    "Node",
    "Address",
    "TaggedAddresses",
    "NodeMeta",
    "Datacenter",
    "Service",
    "Check",
    "Checks",
    "SkipNodeUpdate"
  )

  implicit val catalogServiceFormat: RootJsonFormat[CatalogService] = jsonFormat(
    CatalogService.apply,
    "ID",
    "Node",
    "Address",
    "Datacenter",
    "TaggedAddresses",
    "NodeMeta",
    "ServiceId",
    "ServiceName",
    "ServiceAddress",
    "ServiceTaggedAddresses",
    "ServiceTags",
    "ServiceMeta",
    "ServicePort",
    "ServiceWeights",
    "ServiceEnableTagOverride",
    "ServiceProxy",
    "XreateIndex",
    "Checks",
    "ModifyIndex",
    "Namespace"
  )

  implicit val nodeFormat: RootJsonFormat[Node] =
    jsonFormat(Node.apply, "ID", "Node", "Address", "Datacanter", "TaggedAddresses", "Meta", "CreateIndex", "ModifyIndex")

}
