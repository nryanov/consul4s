package consul4s.sprayJson.model

import consul4s.model.coordinate._
import consul4s.model.coordinate.NodeCoordinate.Coord
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Coordinate extends DefaultJsonProtocol {
  implicit val coordFormat: RootJsonFormat[Coord] = jsonFormat(Coord.apply, "Vec", "Error", "Adjustment", "Height")

  implicit val nodeCoordinateFormat: RootJsonFormat[NodeCoordinate] = jsonFormat(NodeCoordinate.apply, "Node", "Coord")

  implicit val datacenterCoordinateFormat: RootJsonFormat[DatacenterCoordinate] =
    jsonFormat(DatacenterCoordinate.apply, "Datacenter", "AreaID", "Coordinates")

}
