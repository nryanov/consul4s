package consul4s.v1.api

import consul4s.ConsistencyMode
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import sttp.client3._

trait Coordinate[F[_]] { this: ConsulApi[F] =>

  /**
   * GET	/coordinate/datacenters
   * @param token - consul token
   * @return - This endpoint returns the WAN network coordinates for all Consul servers, organized by datacenters.
   */
  def getDatacenterCoordinates(token: Option[String] = None): F[Result[List[DatacenterCoordinate]]] = {
    val requestTemplate = basicRequest.get(uri"$url/coordinate/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asDatacenterCoordinateList)

    sendRequest(request, token)
  }

  /**
   * GET	/coordinate/nodes
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return - This endpoint returns the LAN network coordinates for all nodes in a given datacenter.
   */
  def getNodeCoordinates(
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[NodeCoordinate]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/coordinate/nodes?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateList)

    sendRequest(request, token)
  }

  /**
   * GET	/coordinate/node/:node
   * @param node - node id
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return - This endpoint returns the LAN network coordinates for the given node.
   */
  def getNodeCoordinate(
    node: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[List[NodeCoordinate]]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/coordinate/node/$node?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeCoordinateListOption)

    sendRequest(request, token)
  }

  /**
   * PUT	/coordinate/update
   * This endpoint updates the LAN network coordinates for a node in a given datacenter.
   * @param nodeCoordinate - new coordinates
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param token - consul token
   * @return - unit
   */
  def updateNodeCoordinate(nodeCoordinate: NodeCoordinate, dc: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/coordinate/update?dc=$dc")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeCoordinateToJson(nodeCoordinate), token)
  }
}
