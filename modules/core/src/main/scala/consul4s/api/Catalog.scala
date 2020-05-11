package consul4s.api

import consul4s.model.NodeInfo
import sttp.client._

trait Catalog[F[_]] { this: ConsulApi[F] =>
  // GET	/catalog/datacenters
  def datacenters(): F[Response[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/nodes
  def nodes(
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Response[List[NodeInfo]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/nodes?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeInfosUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/services
  def services(
    dc: Option[String] = None,
    nodeMeta: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[Map[String, List[String]]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, nodeMetaParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/services?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asMapMultipleValuesUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
