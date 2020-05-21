package consul4s.v1.api

import sttp.client._

trait Status[F[_]] { this: ConsulApi[F] =>
  // GET /status/leader
  def raftLeader(dc: Option[String] = None, token: Option[String] = None): F[Result[String]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/leader?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringValue)

    sendRequest(request, token)
  }

  // GET /status/peers
  def raftPeers(dc: Option[String] = None, token: Option[String] = None): F[Result[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/peers?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringList)

    sendRequest(request, token)
  }
}
