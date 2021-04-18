package consul4s.model.coordinate

import NodeCoordinate._

final case class NodeCoordinate(Node: String, Coord: Coord)

object NodeCoordinate {
  final case class Coord(vec: List[Double], error: Double, adjustment: Double, height: Double)
}
