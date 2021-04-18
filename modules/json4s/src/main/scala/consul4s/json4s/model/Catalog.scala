package consul4s.json4s.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import org.json4s.FieldSerializer

trait Catalog {
  val catalogServiceFormat: FieldSerializer[CatalogService] = FieldSerializer[CatalogService]()

  val newCatalogServiceFormat: FieldSerializer[NewCatalogService] = FieldSerializer[NewCatalogService]()

  val nodeFormat: FieldSerializer[Node] = FieldSerializer[Node]()

  val nodeDeregistrationFormat: FieldSerializer[NodeDeregistration] = FieldSerializer[NodeDeregistration]()

  val nodeRegistrationFormat: FieldSerializer[NodeRegistration] = FieldSerializer[NodeRegistration]()

  val nodeServiceListFormat: FieldSerializer[NodeServiceListInternal] = FieldSerializer[NodeServiceListInternal]()

  val nodeServiceMapFormat: FieldSerializer[NodeServiceMap] = FieldSerializer[NodeServiceMap]()

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
