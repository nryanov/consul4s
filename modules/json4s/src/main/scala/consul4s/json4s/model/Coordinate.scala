package consul4s.json4s.model

import consul4s.model.coordinate.NodeCoordinate.Coord
import consul4s.model.coordinate._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Coordinate {
  val datacenterCoordinateFormat = FieldSerializer[DatacenterCoordinate](
    renameTo("datacenter", "Datacenter").orElse(renameTo("areaId", "AreaID")).orElse(renameTo("coordinates", "Coordinates")),
    renameFrom("Datacenter", "datacenter").orElse(renameFrom("AreaID", "areaId")).orElse(renameFrom("Coordinates", "coordinates"))
  )

  val nodeCoordinateFormat = FieldSerializer[NodeCoordinate](
    renameTo("node", "Node").orElse(renameTo("coord", "Coord")),
    renameFrom("Node", "node").orElse(renameFrom("Coord", "coord"))
  )

  val coordFormat = FieldSerializer[Coord](
    renameTo("vec", "Vec")
      .orElse(renameTo("error", "Error"))
      .orElse(renameTo("adjustment", "Adjustment"))
      .orElse(renameTo("height", "Height")),
    renameFrom("Vec", "vec")
      .orElse(renameFrom("Error", "error"))
      .orElse(renameFrom("Adjustment", "adjustment"))
      .orElse(renameFrom("Height", "height"))
  )

  val coordinateAllFieldSerializers = List(datacenterCoordinateFormat, nodeCoordinateFormat, coordFormat)
}
