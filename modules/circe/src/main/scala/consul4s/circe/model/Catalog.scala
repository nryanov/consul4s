package consul4s.circe.model

import consul4s.model.catalog._
import io.circe._
import io.circe.generic.semiauto._

trait Catalog { this: Agent with Health with Common =>
  implicit val serviceEncoder: Encoder[Service] = deriveEncoder[Service]
  implicit val ServiceDecoder: Decoder[Service] = deriveDecoder[Service]

  implicit val nodeDecoder: Decoder[Node] = deriveDecoder[Node]

  implicit val entityDeregistrationDecoder: Decoder[EntityDeregistration] = deriveDecoder[EntityDeregistration]
  implicit val entityDeregistrationEncoder: Encoder[EntityDeregistration] = deriveEncoder[EntityDeregistration]

  implicit val entityRegistrationDecoder: Decoder[EntityRegistration] = deriveDecoder[EntityRegistration]
  implicit val entityRegistrationEncoder: Encoder[EntityRegistration] = deriveEncoder[EntityRegistration]
}
