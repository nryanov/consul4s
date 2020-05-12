package consul4s.api

import consul4s.model.deprecated.KeyValue
import sttp.client._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._

trait KVStore[F[_]] { this: ConsulApi[F] =>

  // GET /kv/:key
  def get(key: String, dc: Option[String] = None, ns: Option[String] = None): F[Response[Option[KeyValue]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List(dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asKeyValuesOption.map(_.flatMap(_.headOption)))

    val response = sttpBackend.send(request)
    response
  }

  // GET /kv/:key?recurse
  def getRecurse(key: String, dc: Option[String] = None, ns: Option[String] = None): F[Response[Option[List[KeyValue]]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("recurse", dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asKeyValuesOption)

    val response = sttpBackend.send(request)
    response
  }

  // GET /kv/:key?keys
  def getKeys(
    key: String,
    dc: Option[String] = None,
    separator: Option[String] = None,
    ns: Option[String] = None
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

  // GET /kv/:key?raw
  def getRaw(key: String, dc: Option[String] = None, ns: Option[String] = None): F[Response[Option[String]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("raw", dcParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = asString.map(_.toOption))

    val response = sttpBackend.send(request)
    response
  }

  // PUT /kv/:key
  def createOrUpdate(
    key: String,
    value: String,
    dc: Option[String] = None,
    flags: Option[Refined[Int, NonNegative]] = None,
    cas: Option[Refined[Int, NonNegative]] = None,
    acquire: Option[String] = None,
    release: Option[String] = None,
    ns: Option[String] = None
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

  // DELETE /kv/:key
  def delete(key: String, cas: Option[Refined[Int, NonNegative]] = None, ns: Option[String] = None): F[Response[Boolean]] = {
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List(casParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // DELETE /kv/:key?recurse
  def deleteRecurse(key: String, cas: Option[Refined[Int, NonNegative]] = None, ns: Option[String] = None): F[Response[Boolean]] = {
    val casParam = cas.map(v => s"cas=$v").getOrElse("")
    val nsParam = ns.map(v => s"ns=$v").getOrElse("")

    val params = List("recurse", casParam, nsParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
