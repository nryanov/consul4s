package consul4s.model.coordinate

import NodeCoordinate._

final case class NodeCoordinate(Node: String, Coord: Coord)

object NodeCoordinate {
  final case class Coord(Vec: List[Double], Error: Double, Adjustment: Double, Height: Double)
}
