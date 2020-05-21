package consul4s.v1.api

import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import sttp.client._

trait Session[F[_]] { this: ConsulApi[F] =>

  // PUT	/session/create
  def createSession(newSession: NewSession, dc: Option[String] = None, token: Option[String] = None): F[Result[SessionId]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/create?dc=$dc").body(jsonEncoder.newSessionToJson(newSession))
    val request = requestTemplate.copy(response = jsonDecoder.asSessionId)

    sendRequest(request, token)
  }

  // PUT	/session/destroy/:uuid
  def deleteSession(sessionId: SessionId, dc: Option[String] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/destroy/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }

  // GET	/session/info/:uuid
  def readSession(sessionId: SessionId, dc: Option[String] = None, token: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/info/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  // GET	/session/node/:node
  def listNodeSession(node: String, dc: Option[String] = None, token: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/node/$node?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  // GET	/session/list
  def listSession(dc: Option[String] = None, token: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/session/list?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }

  // PUT	/session/renew/:uuid
  def renewSession(sessionId: SessionId, dc: Option[String] = None, token: Option[String] = None): F[Result[List[SessionInfo]]] = {
    val requestTemplate = basicRequest.put(uri"$url/session/renew/${sessionId.id}?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asSessionInfoList)

    sendRequest(request, token)
  }
}
