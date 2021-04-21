package consul4s.ziojson.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import zio.json.{DeriveJsonCodec, DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceCodec: JsonCodec[CatalogService] = DeriveJsonCodec.gen[CatalogService]
  implicit val newCatalogServiceEncoder: JsonEncoder[NewCatalogService] = DeriveJsonEncoder.gen[NewCatalogService]
  implicit val newCatalogServiceDecoder: JsonDecoder[NewCatalogService] = DeriveJsonDecoder.gen[NewCatalogService]
  implicit val nodeCodec: JsonCodec[Node] = DeriveJsonCodec.gen[Node]
  implicit val nodeDeregistrationCodec: JsonCodec[NodeDeregistration] = DeriveJsonCodec.gen[NodeDeregistration]
  implicit val nodeRegistrationEncoder: JsonEncoder[NodeRegistration] = DeriveJsonEncoder.gen[NodeRegistration]
  implicit val nodeRegistrationDecoder: JsonDecoder[NodeRegistration] = DeriveJsonDecoder.gen[NodeRegistration]
  implicit val nodeServiceListInternalCodec: JsonCodec[NodeServiceListInternal] = DeriveJsonCodec.gen[NodeServiceListInternal]
  implicit val nodeServiceMapCodec: JsonCodec[NodeServiceMap] = DeriveJsonCodec.gen[NodeServiceMap]
}
