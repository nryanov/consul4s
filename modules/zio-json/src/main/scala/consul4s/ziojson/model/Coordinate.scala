package consul4s.ziojson.model

import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.coordinate.NodeCoordinate.Coord
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Coordinate {
  implicit val coordEncoder: JsonEncoder[Coord] = DeriveJsonEncoder.gen[Coord]
  implicit val nodeCoordinateEncoder: JsonEncoder[NodeCoordinate] = DeriveJsonEncoder.gen[NodeCoordinate]
  implicit val datacenterCoordinateEncoder: JsonEncoder[DatacenterCoordinate] = DeriveJsonEncoder.gen[DatacenterCoordinate]

  implicit val coordDecoder: JsonDecoder[Coord] = DeriveJsonDecoder.gen[Coord]
  implicit val nodeCoordinateDecoder: JsonDecoder[NodeCoordinate] = DeriveJsonDecoder.gen[NodeCoordinate]
  implicit val datacenterCoordinateDecoder: JsonDecoder[DatacenterCoordinate] = DeriveJsonDecoder.gen[DatacenterCoordinate]
}
