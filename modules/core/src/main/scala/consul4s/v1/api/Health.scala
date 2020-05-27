package consul4s.v1.api

import consul4s.{CacheMode, ConsistencyMode, NoCache}
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.{CheckStatus => ConsulStatus}
import sttp.client._

trait Health[F[_]] { this: ConsulApi[F] =>

  /**
   * GET	/health/service/:service
   * This endpoint returns the nodes providing the service indicated on the path.
   * @param service - Specifies the service to list services for. This is provided as part of the URL.
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param tag - Specifies the tag to filter the list.
   * This is specified as part of the URL as a query parameter.
   * Can be used multiple times for additional filtering,
   * returning only the results that include all of the tag values provided.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param passing - Specifies that the server should return only nodes with all checks in the passing state.
   * This can be used to avoid additional filtering on the client side.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @param cacheMode - see [[CacheMode]]
   * @return
   */
  def nodesForService(
    service: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    tag: Option[String] = None,
    nodeMeta: Option[String] = None,
    passing: Boolean = false,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None,
    cacheMode: CacheMode = NoCache
  ): F[Result[List[ServiceEntry]]] = {
    val passingParam = if (passing) "&passing" else ""

    val requestTemplate = basicRequest.get(
      addConsistencyMode(
        uri"$url/health/service/$service?dc=$dc&near=$near&tag=$tag&node-meta=$nodeMeta&filter=$filter$passingParam",
        consistencyMode
      )
    )
    val request = addCacheMode(requestTemplate.copy(response = jsonDecoder.asServiceEntryList), cacheMode)

    sendRequest(request, token)
  }

  /**
   * /health/connect/:service
   * @param service
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param tag - Specifies the tag to filter the list.
   * This is specified as part of the URL as a query parameter.
   * Can be used multiple times for additional filtering,
   * returning only the results that include all of the tag values provided.
   * @param nodeMeta - Specifies a desired node metadata key/value pair of the form key:value.
   * This parameter can be specified multiple times, and will filter the results to nodes
   * with the specified key/value pairs. This is specified as part of the URL as a query parameter.
   * @param passing - Specifies that the server should return only nodes with all checks in the passing state.
   * This can be used to avoid additional filtering on the client side.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param token - consul token
   * @return
   */
  def nodesForConnectCapableService(
    service: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    tag: Option[String] = None,
    nodeMeta: Option[String] = None,
    passing: Boolean = false,
    filter: Option[String] = None,
    token: Option[String] = None
  ): F[Result[List[ServiceEntry]]] = {
    val passingParam = if (passing) "&passing" else ""

    val requestTemplate =
      basicRequest.get(uri"$url/health/connect/$service?dc=$dc&near=$near&tag=$tag&node-meta=$nodeMeta&filter=$filter$passingParam")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceEntryList)

    sendRequest(request, token)
  }

  /**
   * /health/node/:node
   * @param node
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return
   */
  def nodeChecks(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[HealthCheck]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/health/node/$node?dc=$dc&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckList)

    sendRequest(request, token)
  }

  /**
   * /health/checks/:service
   * @param service
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
  def serviceChecks(
    service: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[HealthCheck]]] = {
    val requestTemplate = basicRequest.get(
      addConsistencyMode(uri"$url/health/checks/$service?dc=$dc&near=$near&node-meta=$nodeMeta&filter=$filter", consistencyMode)
    )
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckList)

    sendRequest(request, token)
  }

  /**
   * /health/state/:state
   * @param state
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
  def checksInState(
    state: ConsulStatus,
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[HealthCheck]]] = {
    val requestTemplate = basicRequest.get(
      addConsistencyMode(uri"$url/health/state/${state.value}?dc=$dc&near=$near&node-meta=$nodeMeta&filter=$filter", consistencyMode)
    )
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckList)

    sendRequest(request, token)
  }
}
