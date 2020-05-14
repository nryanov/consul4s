package consul4s.circe.model

import consul4s.model.catalog._
import io.circe._
import io.circe.generic.semiauto._

trait Catalog { this: Agent with Health =>
  implicit val entityDeregistrationEncoder: Encoder[EntityDeregistration] = deriveEncoder[EntityDeregistration]
  implicit val entityRegistrationEncoder: Encoder[EntityRegistration] = deriveEncoder[EntityRegistration]
  implicit val serviceEncoder: Encoder[Service] = deriveEncoder[Service]

  implicit val EntityDeregistrationDecoder: Decoder[EntityDeregistration] = deriveDecoder[EntityDeregistration]
  implicit val EntityRegistrationDecoder: Decoder[EntityRegistration] = deriveDecoder[EntityRegistration]
  implicit val ServiceDecoder: Decoder[Service] = deriveDecoder[Service]

  implicit val nodeDecoder: Decoder[Node] = deriveDecoder[Node]
}
