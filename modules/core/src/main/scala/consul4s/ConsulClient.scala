package consul4s

import consul4s.api._
import consul4s.model.{KeyValue, NodeCheck, NodeInfo, ServiceCheck, State}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.NonNegative
import sttp.client.{SttpBackend, _}

class ConsulClient[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(implicit jsonDecoder: JsonDecoder)
    extends ConsulApi[F] {

  override def get(key: String, dc: Option[String], ns: Option[String]): F[Response[Option[KeyValue]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List(dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asKeyValuesOption.map(_.flatMap(_.headOption)))

    val response = sttpBackend.send(request)
    response
  }

  override def getRecurse(key: String, dc: Option[String], ns: Option[String]): F[Response[Option[List[KeyValue]]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("recurse", dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asKeyValuesOption)

    val response = sttpBackend.send(request)
    response
  }

  override def getKeys(
    key: String,
    dc: Option[String],
    separator: Option[String],
    ns: Option[String]
  ): F[Response[Option[List[String]]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val separatorParam = separator.map(v => s"separator=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("keys", dcParam, separatorParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListOption)

    val response = sttpBackend.send(request)
    response
  }

  override def getRaw(key: String, dc: Option[String], ns: Option[String]): F[Response[Option[String]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("raw", dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = asString.map(_.toOption))

    val response = sttpBackend.send(request)
    response
  }

  override def createOrUpdate(
    key: String,
    value: String,
    dc: Option[String],
    flags: Option[Refined[Int, NonNegative]],
    cas: Option[Refined[Int, NonNegative]],
    acquire: Option[String],
    release: Option[String],
    ns: Option[String]
  ): F[Response[Boolean]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val flagsParam = flags.map(v => s"flags=$v").getOrElse("")
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val acquireParam = acquire.map(v => s"acquire=$v").getOrElse("")
    val releaseParam = release.map(v => s"release=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List(dcParam, flagsParam, casParam, acquireParam, releaseParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.put(uri"$url/kv/$key?$params").body(value)
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def delete(key: String, cas: Option[Refined[Int, NonNegative]], ns: Option[String]): F[Response[Boolean]] = {
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List(casParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def deleteRecurse(key: String, cas: Option[Refined[Int, NonNegative]], ns: Option[String]): F[Response[Boolean]] = {
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("recurse", casParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def raftLeader(dc: Option[String]): F[Response[String]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val params = List(dcParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/status/leader?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asStringUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def raftPeers(dc: Option[String]): F[Response[List[String]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val params = List(dcParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/status/peers?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def nodeChecks(node: String, dc: Option[String], filter: Option[String], ns: Option[String]): F[Response[List[NodeCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/node/$node?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeChecksUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def serviceChecks(
    service: String,
    dc: Option[String],
    near: Option[String],
    nodeMeta: Option[String],
    filter: Option[String],
    ns: Option[String]
  ): F[Response[List[ServiceCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/checks/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceChecksUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def checksInState(
    state: State,
    dc: Option[String],
    near: Option[String],
    nodeMeta: Option[String],
    filter: Option[String],
    ns: Option[String]
  ): F[Response[List[ServiceCheck]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/health/state/${state.value}?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceChecksUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def datacenters(): F[Response[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  override def nodes(
    dc: Option[String],
    near: Option[String],
    nodeMeta: Option[String],
    filter: Option[String]
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

  override def services(dc: Option[String], nodeMeta: Option[String], ns: Option[String]): F[Response[Map[String, List[String]]]] = {
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

object ConsulClient {
  def apply[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(implicit jsonDecoder: JsonDecoder): ConsulClient[F] =
    new ConsulClient(url, sttpBackend)
}
