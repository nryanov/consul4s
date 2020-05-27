package consul4s.v1.api

import consul4s.{CacheMode, ConsistencyMode, NoCache}
import consul4s.model.catalog._
import sttp.client._

trait Catalog[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT /catalog/register
   * @param value
   * @param token - consul token
   * @return
   */
  def registerEntity(value: NodeRegistration, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/register")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeRegistrationToJson(value), token)
  }

  /**
   * PUT /catalog/deregister
   * @param value
   * @param token - consul token
   * @return
   */
  def deregisterEntity(value: NodeDeregistration, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/deregister")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.nodeDeregistrationToJson(value), token)
  }

  /**
   * GET	/catalog/datacenters
   * @param token - consul token
   * @return
   */
  def datacenters(token: Option[String] = None): F[Result[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringList)

    sendRequest(request, token)
  }

  /**
   * GET	/catalog/nodes
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def nodes(
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
   * GET	/catalog/services
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def services(
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
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param tag
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @param cacheMode - see [[CacheMode]]
   * @return
   */
  def nodesInfoForService(
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
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param tag
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def nodesInfoForConnectCapableService(
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
   * GET	/catalog/node/:node
   * @param node - Specifies the name or ID of the node to query. This is specified as part of the URL
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def mapOfServicesForNode(
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
   * GET	/catalog/node-services/:node
   * @param node - Specifies the name or ID of the node to query. This is specified as part of the URL
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def listOfServicesForNode(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[NodeServiceList]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/catalog/node-services/$node?dc=$dc&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asNodeServiceList)

    sendRequest(request, token)
  }
}
