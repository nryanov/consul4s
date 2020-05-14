package consul4s.api

import consul4s.model.kv.KVPair
import sttp.client._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._

trait KVStore[F[_]] { this: ConsulApi[F] =>

  // GET /kv/:key
  def get(key: String, dc: Option[String] = None): F[Response[Option[KVPair]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption.map(_.flatMap(_.headOption)))

    val response = sttpBackend.send(request)
    response
  }

  // GET /kv/:key?recurse
  def getRecurse(key: String, dc: Option[String] = None): F[Response[Option[List[KVPair]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?recurse&dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption)

    val response = sttpBackend.send(request)
    response
  }

  // GET /kv/:key?keys
  def getKeys(
    key: String,
    dc: Option[String] = None,
    separator: Option[String] = None
  ): F[Response[Option[List[String]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?keys&dc=$dc&separator=$separator")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListOption)

    val response = sttpBackend.send(request)
    response
  }

  // GET /kv/:key?raw
  def getRaw(key: String, dc: Option[String] = None): F[Response[Option[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?raw&dc=$dc")
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
    release: Option[String] = None
  ): F[Response[Boolean]] = {
    val requestTemplate = basicRequest
      .put(
        uri"$url/kv/$key?dc=$dc&flags=${flags.map(_.toString())}&cas=${cas.map(_.toString())}&acquire=$acquire&release=$release"
      )
      .body(value)
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // DELETE /kv/:key
  def delete(key: String, cas: Option[Refined[Int, NonNegative]] = None): F[Response[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // DELETE /kv/:key?recurse
  def deleteRecurse(key: String, cas: Option[Refined[Int, NonNegative]] = None): F[Response[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?recurse&cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBooleanUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
