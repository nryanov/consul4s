package consul4s.circe.model

import consul4s.model.catalog._
import io.circe._
import io.circe.generic.semiauto._

trait Catalog { this: Agent with Health with Common =>
  implicit val catalogServiceInfoDecoder: Decoder[CatalogService] = deriveDecoder[CatalogService]

  implicit val nodeDecoder: Decoder[Node] = deriveDecoder[Node]

  implicit val entityDeregistrationDecoder: Decoder[EntityDeregistration] = deriveDecoder[EntityDeregistration]
  implicit val entityDeregistrationEncoder: Encoder[EntityDeregistration] = deriveEncoder[EntityDeregistration]

  implicit val entityRegistrationDecoder: Decoder[EntityRegistration] = deriveDecoder[EntityRegistration]
  implicit val entityRegistrationEncoder: Encoder[EntityRegistration] = deriveEncoder[EntityRegistration]

  implicit val nodeServiceListDecoder: Decoder[NodeServiceList] = deriveDecoder[NodeServiceList]
  implicit val nodeServiceMapDecoder: Decoder[NodeServiceMap] = deriveDecoder[NodeServiceMap]
}
