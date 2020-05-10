package consul4s

import consul4s.api._
import consul4s.model.KeyValue
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.NonNegative
import sttp.client.{SttpBackend, _}

class ConsulClient[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(implicit jsonDecoder: JsonDecoder)
    extends ConsulApi[F] {
  def kvStore(): KVStore[F] = this

  override def get(
    key: String,
    dc: Option[String],
    recurse: Boolean,
    keys: Boolean,
    separator: Option[String],
    ns: Option[String]
  ): F[Response[Option[KeyValue]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val separatorParam = separator.map(v => s"separator=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val recurseParam = if (recurse) "recurse" else ""
    val keysParam = if (keys) "keys" else ""

    val params = List(recurseParam, keysParam, dcParam, separatorParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asKeyValueOptionList.map(_.flatMap(_.headOption)))

    val response = sttpBackend.send(request)
    response
  }

  override def getRaw(
    key: String,
    dc: Option[String],
    recurse: Boolean,
    keys: Boolean,
    separator: Option[String],
    ns: Option[String]
  ): F[Response[Option[String]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val separatorParam = separator.map(v => s"separator=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val recurseParam = if (recurse) "recurse" else ""
    val keysParam = if (keys) "keys" else ""

    val params = List("raw", recurseParam, keysParam, dcParam, separatorParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

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
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    val response = sttpBackend.send(request)
    response
  }

  override def delete(key: String, recurse: Boolean, cas: Option[Refined[Int, NonNegative]], ns: Option[String]): F[Response[Boolean]] = {
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")
    val recurseParam = if (recurse) "recurse" else ""

    val params = List(recurseParam, casParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    val response = sttpBackend.send(request)
    response
  }
}

object ConsulClient {
  def apply[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(implicit jsonDecoder: JsonDecoder): ConsulClient[F] =
    new ConsulClient(url, sttpBackend)
}
