package consul4s.circe.model

import consul4s.model.coordinate._
import consul4s.model.coordinate.NodeCoordinate.Coord
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait Coordinate {
  implicit val coordEncoder: Encoder[Coord] = new Encoder[Coord] {
    override def apply(a: Coord): Json = Json.obj(
      ("Vec", a.Vec.asJson),
      ("Error", a.Error.asJson),
      ("Adjustment", a.Adjustment.asJson),
      ("Height", a.Height.asJson)
    )
  }

  implicit val nodeCoordinateEncoder: Encoder[NodeCoordinate] = new Encoder[NodeCoordinate] {
    override def apply(a: NodeCoordinate): Json = Json.obj(
      ("Node", a.Node.asJson),
      ("Coord", a.Coord.asJson)
    )
  }

  implicit val coordDecoder: Decoder[Coord] = new Decoder[Coord] {
    override def apply(c: HCursor): Result[Coord] = for {
      vec <- c.downField("Vec").as[List[Double]]
      error <- c.downField("Error").as[Double]
      adjustment <- c.downField("Adjustment").as[Double]
      height <- c.downField("Height").as[Double]
    } yield Coord(vec, error, adjustment, height)
  }

  implicit val nodeCoordinateDecoder: Decoder[NodeCoordinate] = new Decoder[NodeCoordinate] {
    override def apply(c: HCursor): Result[NodeCoordinate] = for {
      node <- c.downField("Node").as[String]
      coord <- c.downField("Coord").as[Coord]
    } yield NodeCoordinate(node, coord)
  }

  implicit val datacenterCoordinateDecoder: Decoder[DatacenterCoordinate] = new Decoder[DatacenterCoordinate] {
    override def apply(c: HCursor): Result[DatacenterCoordinate] = for {
      datacenter <- c.downField("Datacenter").as[String]
      areaId <- c.downField("AreaID").as[String]
      coordinates <- c.downField("Coordinates").as[List[NodeCoordinate]]
    } yield DatacenterCoordinate(datacenter, areaId, coordinates)
  }
}
