package consul4s.ziojson.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceEncoder: JsonEncoder[CatalogService] = DeriveJsonEncoder.gen[CatalogService]
  implicit val newCatalogServiceEncoder: JsonEncoder[NewCatalogService] = DeriveJsonEncoder.gen[NewCatalogService]
  implicit val nodeEncoder: JsonEncoder[Node] = DeriveJsonEncoder.gen[Node]
  implicit val nodeDeregistrationEncoder: JsonEncoder[NodeDeregistration] = DeriveJsonEncoder.gen[NodeDeregistration]
  implicit val nodeRegistrationEncoder: JsonEncoder[NodeRegistration] = DeriveJsonEncoder.gen[NodeRegistration]
  implicit val nodeServiceListInternalEncoder: JsonEncoder[NodeServiceListInternal] = DeriveJsonEncoder.gen[NodeServiceListInternal]
  implicit val nodeServiceMapEncoder: JsonEncoder[NodeServiceMap] = DeriveJsonEncoder.gen[NodeServiceMap]

  implicit val catalogServiceDecoder: JsonDecoder[CatalogService] = DeriveJsonDecoder.gen[CatalogService]
  implicit val newCatalogServiceDecoder: JsonDecoder[NewCatalogService] = DeriveJsonDecoder.gen[NewCatalogService]
  implicit val nodeDecoder: JsonDecoder[Node] = DeriveJsonDecoder.gen[Node]
  implicit val nodeDeregistrationDecoder: JsonDecoder[NodeDeregistration] = DeriveJsonDecoder.gen[NodeDeregistration]
  implicit val nodeRegistrationDecoder: JsonDecoder[NodeRegistration] = DeriveJsonDecoder.gen[NodeRegistration]
  implicit val nodeServiceListInternalDecoder: JsonDecoder[NodeServiceListInternal] = DeriveJsonDecoder.gen[NodeServiceListInternal]
  implicit val nodeServiceMapDecoder: JsonDecoder[NodeServiceMap] = DeriveJsonDecoder.gen[NodeServiceMap]
}
