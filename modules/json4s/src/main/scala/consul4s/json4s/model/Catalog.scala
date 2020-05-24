package consul4s.json4s.model

import consul4s.model.catalog._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Catalog {
  val catalogServiceFormat = FieldSerializer[CatalogService](
    Map(),
    renameFrom("ID", "id")
      .orElse(renameFrom("Node", "node"))
      .orElse(renameFrom("Address", "address"))
      .orElse(renameFrom("Datacenter", "datacenter"))
      .orElse(renameFrom("TaggedAddresses", "taggedAddresses"))
      .orElse(renameFrom("NodeMeta", "nodeMeta"))
      .orElse(renameFrom("ServiceKind", "serviceKind"))
      .orElse(renameFrom("ServiceID", "serviceID"))
      .orElse(renameFrom("ServiceName", "serviceName"))
      .orElse(renameFrom("ServiceTags", "serviceTags"))
      .orElse(renameFrom("ServiceAddress", "serviceAddress"))
      .orElse(renameFrom("ServiceTaggedAddresses", "serviceTaggedAddresses"))
      .orElse(renameFrom("ServiceWeights", "serviceWeights"))
      .orElse(renameFrom("ServiceMeta", "serviceMeta"))
      .orElse(renameFrom("ServicePort", "servicePort"))
      .orElse(renameFrom("ServiceEnableTagOverride", "serviceEnableTagOverride"))
  )

  val newCatalogServiceFormat = FieldSerializer[NewCatalogService](
    renameTo("service", "Service")
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("tags", "Tags"))
      .orElse(renameTo("address", "Address"))
      .orElse(renameTo("taggedAddresses", "TaggedAddresses"))
      .orElse(renameTo("meta", "Meta"))
      .orElse(renameTo("port", "Port"))
      .orElse(renameTo("enableTagOverride", "EnableTagOverride"))
      .orElse(renameTo("weights", "Weights")),
    Map()
  )

  val nodeFormat = FieldSerializer[Node](
    Map(),
    renameFrom("Node", "node")
      .orElse(renameFrom("Address", "address"))
      .orElse(renameFrom("ID", "id"))
      .orElse(renameFrom("Datacenter", "datacenter"))
      .orElse(renameFrom("TaggedAddresses", "taggedAddresses"))
      .orElse(renameFrom("Meta", "meta"))
  )

  val nodeDeregistrationFormat = FieldSerializer[NodeDeregistration](
    renameTo("node", "Node")
      .orElse(renameTo("datacenter", "Datacenter"))
      .orElse(renameTo("checkId", "CheckID"))
      .orElse(renameTo("serviceId", "ServiceID")),
    Map()
  )

  val nodeRegistrationFormat = FieldSerializer[NodeRegistration](
    renameTo("node", "Node")
      .orElse(renameTo("address", "Address"))
      .orElse(renameTo("service", "Service"))
      .orElse(renameTo("check", "Check"))
      .orElse(renameTo("checks", "Checks"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("datacenter", "Datacenter"))
      .orElse(renameTo("taggedAddresses", "TaggedAddresses"))
      .orElse(renameTo("nodeMeta", "NodeMeta"))
      .orElse(renameTo("skipNodeUpdate", "SkipNodeUpdate")),
    Map()
  )

  val nodeServiceListFormat = FieldSerializer[NodeServiceList](
    Map(),
    renameFrom("Node", "node").orElse(renameFrom("Services", "services"))
  )

  val nodeServiceMapFormat = FieldSerializer[NodeServiceMap](
    Map(),
    renameFrom("Node", "node").orElse(renameFrom("Services", "services"))
  )

  val catalogAllFieldSerializers = List(
    catalogServiceFormat,
    newCatalogServiceFormat,
    nodeFormat,
    nodeDeregistrationFormat,
    nodeRegistrationFormat,
    nodeServiceListFormat,
    nodeServiceMapFormat
  )
}
