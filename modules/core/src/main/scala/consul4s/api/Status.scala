package consul4s.api

import sttp.client._

trait Status[F[_]] { this: ConsulApi[F] =>
  // GET /status/leader
  def raftLeader(dc: Option[String] = None): F[Response[String]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/leader?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET /status/peers
  def raftPeers(dc: Option[String] = None): F[Response[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/peers?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
