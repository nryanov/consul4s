package consul4s.zio.json.model

import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.coordinate.NodeCoordinate.Coord
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Coordinate {
  private[zio] case class CoordRepr(vec: List[Double], error: Double, adjustment: Double, height: Double)

  implicit val coordCodec: JsonCodec[Coord] = ConverterMacros.derive[CoordRepr, Coord]

  private[zio] case class NodeCoordinateRepr(node: String, coord: Coord)

  implicit val nodeCoordinateCodec: JsonCodec[NodeCoordinate] = ConverterMacros.derive[NodeCoordinateRepr, NodeCoordinate]

  private[zio] case class DatacenterCoordinateRepr(datacenter: String, areaId: String, coordinates: List[NodeCoordinate])

  implicit val datacenterCoordinateCodec: JsonCodec[DatacenterCoordinate] =
    ConverterMacros.derive[DatacenterCoordinateRepr, DatacenterCoordinate]
}
