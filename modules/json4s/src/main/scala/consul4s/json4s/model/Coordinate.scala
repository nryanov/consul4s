package consul4s.json4s.model

import consul4s.model.coordinate.NodeCoordinate.Coord
import consul4s.model.coordinate._
import org.json4s.FieldSerializer

trait Coordinate {
  val datacenterCoordinateFormat: FieldSerializer[DatacenterCoordinate] = FieldSerializer[DatacenterCoordinate]()

  val nodeCoordinateFormat: FieldSerializer[NodeCoordinate] = FieldSerializer[NodeCoordinate]()

  val coordFormat: FieldSerializer[Coord] = FieldSerializer[Coord]()

  val coordinateAllFieldSerializers = List(datacenterCoordinateFormat, nodeCoordinateFormat, coordFormat)
}
