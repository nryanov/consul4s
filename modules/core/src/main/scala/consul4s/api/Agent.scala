package consul4s.api

import consul4s.model.{MemberInfo, ServiceInfo}
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

  // GET	/agent/services
  def servicesInfo(filter: Option[String] = None): F[Response[Map[String, ServiceInfo]]] = {
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")

    val params = List(filterParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/agent/services?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServicesInfoMapUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
