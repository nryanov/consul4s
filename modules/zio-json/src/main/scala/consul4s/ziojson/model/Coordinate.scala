package consul4s.ziojson.model

import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.coordinate.NodeCoordinate.Coord
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Coordinate {
  implicit val coordCodec: JsonCodec[Coord] = DeriveJsonCodec.gen[Coord]
  implicit val nodeCoordinateCodec: JsonCodec[NodeCoordinate] = DeriveJsonCodec.gen[NodeCoordinate]
  implicit val datacenterCoordinateCodec: JsonCodec[DatacenterCoordinate] = DeriveJsonCodec.gen[DatacenterCoordinate]
}
