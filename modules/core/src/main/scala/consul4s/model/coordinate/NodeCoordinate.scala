package consul4s.model.coordinate

import NodeCoordinate._

final case class NodeCoordinate(node: String, coord: Coord)

object NodeCoordinate {
  final case class Coord(vec: List[Double], error: Double, adjustment: Double, height: Double)
}
