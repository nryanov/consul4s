package consul4s.circe.model

import consul4s.model.catalog._
import io.circe._
import io.circe.generic.semiauto._

trait Catalog { this: Agent with Health =>
  implicit val catalogDeregistrationEncoder: Encoder[CatalogDeregistration] = deriveEncoder[CatalogDeregistration]
  implicit val catalogNodeEncoder: Encoder[CatalogNode] = deriveEncoder[CatalogNode]
  implicit val catalogNodeServiceListEncoder: Encoder[CatalogNodeServiceList] = deriveEncoder[CatalogNodeServiceList]
  implicit val catalogRegistrationEncoder: Encoder[CatalogRegistration] = deriveEncoder[CatalogRegistration]
  implicit val catalogServiceEncoder: Encoder[CatalogService] = deriveEncoder[CatalogService]
  implicit val nodeEncoder: Encoder[Node] = deriveEncoder[Node]
  implicit val serviceAddressEncoder: Encoder[ServiceAddress] = deriveEncoder[ServiceAddress]
  implicit val weightsEncoder: Encoder[Weights] = deriveEncoder[Weights]

  implicit val catalogDeregistrationDecoder: Decoder[CatalogDeregistration] = deriveDecoder[CatalogDeregistration]
  implicit val catalogNodeDecoder: Decoder[CatalogNode] = deriveDecoder[CatalogNode]
  implicit val catalogNodeServiceListDecoder: Decoder[CatalogNodeServiceList] = deriveDecoder[CatalogNodeServiceList]
  implicit val catalogRegistrationDecoder: Decoder[CatalogRegistration] = deriveDecoder[CatalogRegistration]
  implicit val catalogServiceDecoder: Decoder[CatalogService] = deriveDecoder[CatalogService]
  implicit val nodeDecoder: Decoder[Node] = deriveDecoder[Node]
  implicit val serviceAddressDecoder: Decoder[ServiceAddress] = deriveDecoder[ServiceAddress]
  implicit val weightsDecoder: Decoder[Weights] = deriveDecoder[Weights]
}
