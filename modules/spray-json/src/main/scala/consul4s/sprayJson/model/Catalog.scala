package consul4s.sprayJson.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Catalog extends DefaultJsonProtocol { this: Health with Agent with Common =>
  implicit val catalogServiceFormat: RootJsonFormat[CatalogService] = jsonFormat(
    CatalogService.apply,
    "ID",
    "Node",
    "Address",
    "Datacenter",
    "TaggedAddresses",
    "NodeMeta",
    "ServiceKind",
    "ServiceID",
    "ServiceName",
    "ServiceTags",
    "ServiceAddress",
    "ServiceTaggedAddresses",
    "ServiceWeights",
    "ServiceMeta",
    "ServicePort",
    "ServiceEnableTagOverride"
  )

  implicit val newCatalogServiceFormat: RootJsonFormat[NewCatalogService] = jsonFormat(
    NewCatalogService.apply,
    "Service",
    "ID",
    "Tags",
    "Address",
    "TaggedAddresses",
    "Meta",
    "Port",
    "EnableTagOverride",
    "Weights"
  )

  implicit val nodeFormat: RootJsonFormat[Node] = jsonFormat(Node.apply, "Node", "Address", "ID", "Datacenter", "TaggedAddresses", "Meta")

  implicit val nodeDeregistrationFormat: RootJsonFormat[NodeDeregistration] =
    jsonFormat(NodeDeregistration.apply, "Node", "Datacenter", "CheckID", "ServiceID")

  implicit val nodeRegistrationFormat: RootJsonFormat[NodeRegistration] = rootFormat(
    lazyFormat(
      jsonFormat(
        NodeRegistration.apply,
        "Node",
        "Address",
        "Service",
        "Check",
        "Checks",
        "ID",
        "Datacenter",
        "TaggedAddresses",
        "NodeMeta",
        "SkipNodeUpdate"
      )
    )
  )

  implicit val nodeServiceListFormat: RootJsonFormat[NodeServiceListInternal] =
    jsonFormat(NodeServiceListInternal.apply, "Node", "Services")

  implicit val nodeServiceMapFormat: RootJsonFormat[NodeServiceMap] = jsonFormat(NodeServiceMap.apply, "Node", "Services")

}
