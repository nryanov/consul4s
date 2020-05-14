package consul4s.api

import consul4s.model.session.{SessionId, SessionInfo}
import sttp.client._

trait Session[F[_]] { this: ConsulApi[F] =>

  // PUT	/session/create
  def createSession(sessionInfo: SessionInfo, dc: Option[String] = None): F[Response[SessionId]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/create?dc=$dc").body(jsonEncoder.sessionToJson(sessionInfo))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionIdUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/session/destroy/:uuid
  def deleteSession(sessionId: SessionId, dc: Option[String] = None): F[Response[Boolean]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/destroy/${sessionId.ID}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/info/:uuid
  def readSession(sessionId: SessionId, dc: Option[String] = None): F[Response[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/info/${sessionId.ID}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/node/:node
  def listNodeSession(node: String, dc: Option[String] = None): F[Response[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/node/$node?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/list
  def listSession(dc: Option[String] = None): F[Response[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/list/?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/session/renew/:uuid
  def renewSession(sessionId: SessionId, dc: Option[String] = None): F[Response[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/renew/${sessionId.ID}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionListUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
