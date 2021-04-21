package consul4s.ziojson.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceCodec: JsonCodec[CatalogService] = DeriveJsonCodec.gen[CatalogService]
  implicit val newCatalogServiceCodec: JsonCodec[NewCatalogService] = DeriveJsonCodec.gen[NewCatalogService]
  implicit val nodeCodec: JsonCodec[Node] = DeriveJsonCodec.gen[Node]
  implicit val nodeDeregistrationCodec: JsonCodec[NodeDeregistration] = DeriveJsonCodec.gen[NodeDeregistration]
  implicit val nodeRegistrationCodec: JsonCodec[NodeRegistration] = DeriveJsonCodec.gen[NodeRegistration]
  implicit val nodeServiceListInternalCodec: JsonCodec[NodeServiceListInternal] = DeriveJsonCodec.gen[NodeServiceListInternal]
  implicit val nodeServiceMapCodec: JsonCodec[NodeServiceMap] = DeriveJsonCodec.gen[NodeServiceMap]
}
