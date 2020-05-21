package consul4s.v1.api

import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import sttp.client._

trait Coordinate[F[_]] { this: ConsulApi[F] =>
  // GET	/coordinate/datacenters
  def coordinateWANDatacenters(): F[Result[List[DatacenterCoordinate]]] = {
    val requestTemplate = basicRequest.get(uri"$url/coordinate/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asDatacenterCoordinateList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/coordinate/nodes
  def coordinateLANNodes(dc: Option[String] = None): F[Result[List[NodeCoordinate]]] = {
    val requestTemplate = basicRequest.get(uri"$url/coordinate/nodes?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/coordinate/node/:node
  def coordinateLANNode(node: String, dc: Option[String] = None): F[Result[Option[List[NodeCoordinate]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/coordinate/node/$node?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateListOption)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/coordinate/update
  def coordinateUpdate(nodeCoordinate: NodeCoordinate, dc: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/coordinate/update?dc=$dc")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.nodeCoordinateToJson(nodeCoordinate))

    val response = sttpBackend.send(request)
    response
  }
}
