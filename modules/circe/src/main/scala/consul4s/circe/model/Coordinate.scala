package consul4s.circe.model

import consul4s.model.coordinate._
import io.circe._
import io.circe.generic.semiauto._

trait Coordinate {
  implicit val coordEncoder: Encoder[Coord] = deriveEncoder[Coord]
  implicit val nodeCoordinateEncoder: Encoder[NodeCoordinate] = deriveEncoder[NodeCoordinate]
  implicit val datacenterCoordinateEncoder: Encoder[DatacenterCoordinate] = deriveEncoder[DatacenterCoordinate]

  implicit val coordDecoder: Decoder[Coord] = deriveDecoder[Coord]
  implicit val nodeCoordinateDecoder: Decoder[NodeCoordinate] = deriveDecoder[NodeCoordinate]
  implicit val datacenterCoordinateDecoder: Decoder[DatacenterCoordinate] = deriveDecoder[DatacenterCoordinate]
}
