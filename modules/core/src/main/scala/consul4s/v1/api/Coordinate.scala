package consul4s.v1.api

import consul4s.ConsistencyMode
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import sttp.client._

trait Coordinate[F[_]] { this: ConsulApi[F] =>
  // GET	/coordinate/datacenters
  def coordinateWANDatacenters(token: Option[String] = None): F[Result[List[DatacenterCoordinate]]] = {
    val requestTemplate = basicRequest.get(uri"$url/coordinate/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asDatacenterCoordinateList)

    sendRequest(request, token)
  }

  // GET	/coordinate/nodes
  def coordinateLANNodes(
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[NodeCoordinate]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/coordinate/nodes?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateList)

    sendRequest(request, token)
  }

  // GET	/coordinate/node/:node
  def coordinateLANNode(
    node: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[List[NodeCoordinate]]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/coordinate/node/$node?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateListOption)

    sendRequest(request, token)
  }

  // PUT	/coordinate/update
  def coordinateUpdate(nodeCoordinate: NodeCoordinate, dc: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/coordinate/update?dc=$dc")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeCoordinateToJson(nodeCoordinate), token)
  }
}
