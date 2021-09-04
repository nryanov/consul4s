package consul4s.v1.api

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.{CacheMode, ConsistencyMode, NoCache}
import consul4s.model.catalog._
import sttp.client3._

trait Catalog[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT /catalog/register This endpoint is a low-level mechanism for registering or updating entries in the catalog.
   * @param value
   *   - new entity
   * @param token
   *   - consul token
   * @return
   *   - unit
   */
  def registerEntity(value: NodeRegistration, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/register")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeRegistrationToJson(value), token)
  }

  /**
   * PUT /catalog/deregister This endpoint is a low-level mechanism for directly removing entries from the Catalog. I
   * @param value
   *   - entity filter
   * @param token
   *   - consul token
   * @return
   *   - unit
   */
  def deregisterEntity(value: NodeDeregistration, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/deregister")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeDeregistrationToJson(value), token)
  }

  /**
   * GET /catalog/datacenters
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the list of all known datacenters.
   */
  def getDatacenters(token: Option[String] = None): F[Result[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringList)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/nodes
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param near
   *   - Specifies to sort the resulting list in ascending order based on the estimated round trip time from that node. Passing ?near=_agent
   *     will use the agent's node for the sort. Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For
   *     header to lookup the node to use for the sort. If this is not present, the default behavior will shuffle the nodes randomly each
   *     time the query is executed.
   * @param nodeMeta
   *   - Specifies a desired node metadata key/value pair of the form key:value. This parameter can be specified multiple times, and will
   *     filter the results to nodes with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter
   *   - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the nodes registered in a given datacenter.
   */
  def getDatacenterNodes(
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[Node]]] = {
    val requestTemplate =
      basicRequest.get(addConsistencyMode(uri"$url/catalog/nodes?dc=$dc&near=$near&node-meta=$nodeMeta&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeList)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/services
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param nodeMeta
   *   - Specifies a desired node metadata key/value pair of the form key:value. This parameter can be specified multiple times, and will
   *     filter the results to nodes with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the services registered in a given datacenter.
   */
  def getDatacenterServiceNames(
    dc: Option[String] = None,
    nodeMeta: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Map[String, List[String]]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/catalog/services?dc=$dc&node-meat=$nodeMeta", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asMapMultipleValues)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/service/ + service
   * @param service
   *   - Specifies the name of the service for which to list nodes. This is specified as part of the URL.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param tag
   *   - Specifies the tag to filter on. This is specified as part of the URL as a query parameter. Can be used multiple times for
   *     additional filtering, returning only the results that include all of the tag values provided.
   * @param near
   *   - Specifies to sort the resulting list in ascending order based on the estimated round trip time from that node. Passing ?near=_agent
   *     will use the agent's node for the sort. Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For
   *     header to lookup the node to use for the sort. If this is not present, the default behavior will shuffle the nodes randomly each
   *     time the query is executed.
   * @param nodeMeta
   *   - Specifies a desired node metadata key/value pair of the form key:value. This parameter can be specified multiple times, and will
   *     filter the results to nodes with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter
   *   - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @param cacheMode
   *   - see [[CacheMode]]
   * @return
   *   - This endpoint returns the nodes providing a service in a given datacenter.
   */
  // todo: tag may be used multiple times
  def getDatacenterServices(
    service: String,
    dc: Option[String] = None,
    tag: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None,
    cacheMode: CacheMode = NoCache
  ): F[Result[List[CatalogService]]] = {
    val requestTemplate = basicRequest.get(
      addConsistencyMode(uri"$url/catalog/service/$service?dc=$dc&tag=$tag&near=$near&node-meta=$nodeMeta&filter=$filter", consistencyMode)
    )
    val request = addCacheMode(requestTemplate.copy(response = jsonDecoder.asCatalogServiceList), cacheMode)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/connect/ + service
   * @param service
   *   - Specifies the name of the service for which to list nodes. This is specified as part of the URL.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param tag
   *   - Specifies the tag to filter on. This is specified as part of the URL as a query parameter. Can be used multiple times for
   *     additional filtering, returning only the results that include all of the tag values provided.
   * @param near
   *   - Specifies to sort the resulting list in ascending order based on the estimated round trip time from that node. Passing ?near=_agent
   *     will use the agent's node for the sort. Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For
   *     header to lookup the node to use for the sort. If this is not present, the default behavior will shuffle the nodes randomly each
   *     time the query is executed.
   * @param nodeMeta
   *   - Specifies a desired node metadata key/value pair of the form key:value. This parameter can be specified multiple times, and will
   *     filter the results to nodes with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter
   *   - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the nodes providing a Connect-capable service in a given datacenter.
   */
  // todo: tag may be used multiple times
  def getConnectCapableDatacenterServices(
    service: String,
    dc: Option[String] = None,
    tag: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[CatalogService]]] = {
    val requestTemplate = basicRequest.get(
      addConsistencyMode(uri"$url/catalog/connect/$service?dc=$dc&tag=$tag&near=$near&node-meta=$nodeMeta&filter=$filter", consistencyMode)
    )
    val request = requestTemplate.copy(response = jsonDecoder.asCatalogServiceList)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/node/:node
   * @param node
   *   - Specifies the name or ID of the node to query. This is specified as part of the URL
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param filter
   *   - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the node's registered services.
   */
  def getMapOfNodeServices(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[NodeServiceMap]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/catalog/node/$node?dc=$dc&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeServiceMap)

    sendRequest(request, token)
  }

  /**
   * GET /catalog/node-services/:node
   * @param node
   *   - Specifies the name or ID of the node to query. This is specified as part of the URL
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param filter
   *   - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - This endpoint returns the node's registered services.
   */
  def getListOfNodeServices(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[NodeServiceList]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/catalog/node-services/$node?dc=$dc&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeServiceListInternal.mapRight {
      case NodeServiceListInternal(Some(node), Some(services)) => Some(NodeServiceList(node, services))
      case _                                                   => None
    })

    sendRequest(request, token)
  }
}
