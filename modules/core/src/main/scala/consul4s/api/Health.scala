package consul4s.api

import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.{Status => ConsulStatus}
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
    ns: Option[String] = None
  ): F[Response[List[ServiceEntry]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"nodeMeta=$v").getOrElse("")
    val passingParam = if (passing) "passing" else ""
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params =
      List(dcParam, nearParam, tagParam, nodeMetaParam, passingParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/service/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceEntriesUnsafe)

    val response = sttpBackend.send(request)
    response
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
    ns: Option[String] = None
  ): F[Response[List[ServiceEntry]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"nodeMeta=$v").getOrElse("")
    val passingParam = if (passing) "passing" else ""
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params =
      List(dcParam, nearParam, tagParam, nodeMetaParam, passingParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/connect/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceEntriesUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/health/node/:node
  def nodeChecks(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[List[HealthCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/node/$node?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthChecksUnsafe)

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
    ns: Option[String] = None
  ): F[Response[List[HealthCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/checks/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthChecksUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/health/state/:state
  def checksInState(
    state: ConsulStatus,
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[List[HealthCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/state/${state.value}?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthChecksUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
