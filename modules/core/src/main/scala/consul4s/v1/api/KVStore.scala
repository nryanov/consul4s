package consul4s.v1.api

import consul4s.model.kv.KVPair
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._
import sttp.client._

trait KVStore[F[_]] { this: ConsulApi[F] =>

  // GET /kv/:key
  def get(key: String, dc: Option[String] = None, token: Option[String] = None): F[Result[Option[KVPair]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption.mapRight(_.flatMap(_.headOption)))

    sendRequest(request, token)
  }

  // GET /kv/:key?recurse
  def getRecurse(key: String, dc: Option[String] = None, token: Option[String] = None): F[Result[Option[List[KVPair]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?recurse&dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption)

    sendRequest(request, token)
  }

  // GET /kv/:key?keys
  def getKeys(
    key: String,
    dc: Option[String] = None,
    separator: Option[String] = None,
    token: Option[String] = None
  ): F[Result[Option[List[String]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?keys&dc=$dc&separator=$separator")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListOption)

    sendRequest(request, token)
  }

  // GET /kv/:key?raw
  def getRaw(key: String, dc: Option[String] = None, token: Option[String] = None): F[Result[Option[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/kv/$key?raw&dc=$dc")
    val request = requestTemplate.copy(response = asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.code.isSuccess) {
        Right(Some(str))
      } else if (meta.code.code == 404) { // If no key exists at the given path, a 404 is returned instead of a 200 response.
        Right(None)
      } else {
        Left[ResponseError[Exception], Option[String]](HttpError(str))
      }
    })

    sendRequest(request, token)
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
    token: Option[String] = None
  ): F[Result[Boolean]] = {
    val requestTemplate = basicRequest
      .put(
        uri"$url/kv/$key?dc=$dc&flags=${flags.map(_.toString())}&cas=${cas.map(_.toString())}&acquire=$acquire&release=$release"
      )
      .body(value)
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }

  // DELETE /kv/:key
  def delete(key: String, cas: Option[Refined[Int, NonNegative]] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }

  // DELETE /kv/:key?recurse
  def deleteRecurse(key: String, cas: Option[Refined[Int, NonNegative]] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?recurse&cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }
}
