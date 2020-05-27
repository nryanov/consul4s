package consul4s.v1.api

import consul4s.{CacheMode, NoCache}
import consul4s.model.query.QueryResult
import sttp.client._

trait PreparedQuery[F[_]] { this: ConsulApi[F] =>

  /**
   *  GET	/query/:uuid/execute
   * This endpoint executes an existing prepared query.
   * @param queryUUID - Specifies the UUID of the query to execute.
   * This is required and is specified as part of the URL path.
   * This can also be the name of an existing prepared query, or a name that matches a prefix name
   * for a prepared query template.
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param near - Specifies to sort the resulting list in ascending order based on the estimated round trip time
   * from that node. Passing ?near=_agent will use the agent's node for the sort.
   * Passing ?near=_ip will use the source IP of the request or the value of the X-Forwarded-For header
   * to lookup the node to use for the sort. If this is not present,
   * the default behavior will shuffle the nodes randomly each time the query is executed.
   * @param limit - Limit the size of the list to the given number of nodes.
   * This is applied after any sorting or shuffling.
   * @param connect - If true, limit results to nodes that are Connect-capable only.
   * This can also be specified directly on the template itself to force all executions of a query
   * to be Connect-only. See the template documentation for more information.
   * @param token - consul token
   * @param cacheMode - see [[CacheMode]]
   * @return - query result if query exists otherwise None.
   */
  def executeQuery(
    queryUUID: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    limit: Option[Int] = None,
    connect: Boolean = false,
    token: Option[String] = None,
    cacheMode: CacheMode = NoCache
  ): F[Result[Option[QueryResult]]] = {
    val requestTemplate = basicRequest.get(uri"$url/query/$queryUUID/execute?dc=$dc&near=$near&limit=$limit&connect=$connect")
    val request = addCacheMode(requestTemplate.copy(response = jsonDecoder.asQueryResultOption), cacheMode)

    sendRequest(request, token)
  }

}
