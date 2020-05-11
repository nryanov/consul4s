package consul4s.api

import consul4s.model.MemberInfo
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>
  // GET	/agent/members
  def members(wan: Boolean = false, segment: Option[String] = None): F[Response[List[MemberInfo]]] = {
    val wanParam = if (wan) "wan" else ""
    val segmentParam = segment.map(v => s"segment=$v").getOrElse("")

    val params = List(wanParam, segmentParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/agent/members?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asMembersInfoUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
