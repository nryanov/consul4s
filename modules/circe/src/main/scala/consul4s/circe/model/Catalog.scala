package consul4s.circe.model

import consul4s.model.catalog._
import io.circe._
import io.circe.generic.semiauto._

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceInfoDecoder: Decoder[CatalogService] = deriveDecoder[CatalogService]
  implicit val newCatalogServiceInfoEncoder: Encoder[NewCatalogService] = deriveEncoder[NewCatalogService]

  implicit val nodeDecoder: Decoder[Node] = deriveDecoder[Node]

  implicit val entityDeregistrationEncoder: Encoder[NodeDeregistration] = deriveEncoder[NodeDeregistration]

  implicit val entityRegistrationEncoder: Encoder[NodeRegistration] = deriveEncoder[NodeRegistration]

  implicit val nodeServiceListDecoder: Decoder[NodeServiceList] = deriveDecoder[NodeServiceList]
  implicit val nodeServiceMapDecoder: Decoder[NodeServiceMap] = deriveDecoder[NodeServiceMap]
}
