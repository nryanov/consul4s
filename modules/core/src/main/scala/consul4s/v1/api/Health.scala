package consul4s.v1.api

import consul4s.{CacheMode, ConsistencyMode, NoCache}
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.{CheckStatus => ConsulStatus}
import sttp.client._

trait Health[F[_]] { this: ConsulApi[F] =>

  // GET	/health/service/:service
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

  // GET	/health/connect/:service
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

  // GET	/health/node/:node
  def nodeChecks(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[HealthCheck]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/health/node/$node?dc=$dc&filter=$filter", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/health/checks/:service
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

  // GET	/health/state/:state
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
