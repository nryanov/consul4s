package consul4s.api

import sttp.client._

trait Status[F[_]] { this: ConsulApi[F] =>
  // GET /status/leader
  def raftLeader(dc: Option[String] = None): F[Response[String]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val params = List(dcParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/status/leader?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asStringUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET /status/peers
  def raftPeers(dc: Option[String] = None): F[Response[List[String]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val params = List(dcParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/status/peers?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
