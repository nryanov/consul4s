package consul4s.v1.api

import consul4s.ConsistencyMode
import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import sttp.client3._

trait Session[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT	/session/create
   * This endpoint initializes a new session.
   * Sessions must be associated with a node and may be associated with any number of checks.
   * @param newSession - session definition
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param token - consul token
   * @return - ID of the created session
   */
  def createSession(newSession: NewSession, dc: Option[String] = None, token: Option[String] = None): F[Result[SessionId]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/create?dc=$dc").body(jsonEncoder.newSessionToJson(newSession))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionId)

    sendRequest(request, token)
  }

  /**
   * PUT	/session/destroy/:uuid
   * This endpoint destroys the session with the given name. If the session UUID is malformed, an error is returned.
   * If the session UUID does not exist or already expired, a 200 is still returned (the operation is idempotent).
   * @param sessionId - session ID
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param token - consul token
   * @return - true if session was deleted otherwise false
   */
  def deleteSession(sessionId: SessionId, dc: Option[String] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/destroy/${sessionId.ID}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }

  /**
   * GET	/session/info/:uuid
   * This endpoint returns the requested session information.
   * @param sessionId - session id
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return - session info if session exists otherwise None
   */
  def getSessionInfo(
    sessionId: SessionId,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/session/info/${sessionId.ID}?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  /**
   * GET	/session/node/:node
   * This endpoint returns the active sessions for a given node.
   * @param node - Specifies the name or ID of the node to query. This is required and is specified as part of the URL path.
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return - list of active sessions for specified node
   */
  def getListOfActiveNodeSessions(
    node: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/session/node/$node?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  /**
   * GET	/session/list
   * This endpoint returns the list of active sessions.
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode - see [[ConsistencyMode]]
   * @param token - consul token
   * @return - list of active sessions
   */
  def getListOfActiveSessions(
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/session/list?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  /**
   * PUT	/session/renew/:uuid
   * This endpoint renews the given session.
   * This is used with sessions that have a TTL, and it extends the expiration by the TTL.
   * @param sessionId - Specifies the UUID of the session to renew. This is required and is specified as part of the URL path.
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param token - consul token
   * @return - updated session or None if session does not exist
   */
  def renewSession(sessionId: SessionId, dc: Option[String] = None, token: Option[String] = None): F[Result[Option[SessionInfo]]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/renew/${sessionId.ID}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList.mapRight(_.headOption))

    sendRequest(request, token)
  }
}
