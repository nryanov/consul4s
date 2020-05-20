package consul4s.api

import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import sttp.client._

trait Session[F[_]] { this: ConsulApi[F] =>

  // PUT	/session/create
  def createSession(newSession: NewSession, dc: Option[String] = None): F[Result[SessionId]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/create?dc=$dc").body(jsonEncoder.newSessionToJson(newSession))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionId)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/session/destroy/:uuid
  def deleteSession(sessionId: SessionId, dc: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/destroy/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/info/:uuid
  def readSession(sessionId: SessionId, dc: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/info/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/node/:node
  def listNodeSession(node: String, dc: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/node/$node?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/session/list
  def listSession(dc: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/list?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/session/renew/:uuid
  def renewSession(sessionId: SessionId, dc: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/renew/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    val response = sttpBackend.send(request)
    response
  }
}
