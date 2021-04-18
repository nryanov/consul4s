package consul4s.sprayJson.model

import consul4s.model.coordinate._
import consul4s.model.coordinate.NodeCoordinate.Coord
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Coordinate extends DefaultJsonProtocol {
  implicit val coordFormat: RootJsonFormat[Coord] = jsonFormat4(Coord.apply)

  implicit val nodeCoordinateFormat: RootJsonFormat[NodeCoordinate] = jsonFormat2(NodeCoordinate.apply)

  implicit val datacenterCoordinateFormat: RootJsonFormat[DatacenterCoordinate] = jsonFormat3(DatacenterCoordinate.apply)

}
