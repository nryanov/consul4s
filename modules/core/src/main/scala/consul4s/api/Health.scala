package consul4s.api

import consul4s.model.{NodeCheck, ServiceCheck, State}
import sttp.client.Response

trait Health[F[_]] {
  // GET	/health/node/:node
  def nodeChecks(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[List[NodeCheck]]]

  // GET	/health/checks/:service
  def serviceChecks(
    service: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[List[ServiceCheck]]]

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
    //todo: case class
  ): F[Response[List[String]]] = ???

  // GET	/health/connect/:service
  // Parameters and response format are the same as /health/service/:service.
  def nodesForConnectCapableService(
    service: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    tag: Option[String] = None,
    nodeMeta: Option[String] = None,
    passing: Boolean = false,
    filter: Option[String] = None,
    ns: Option[String] = None
    //todo: case class
  ): F[Response[List[String]]] = ???

  // GET	/health/state/:state
  def checksInState(
    state: State,
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[List[ServiceCheck]]]
}
