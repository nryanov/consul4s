package consul4s.v1.api

import consul4s.ConsulResponseError
import consul4s.ConsistencyMode
import consul4s.model.kv.KVPair
import sttp.client3._

trait KVStore[F[_]] { this: ConsulApi[F] =>

  /**
   * GET /kv/:key This endpoint returns the specified key.
   * @param key
   *   - Specifies the path of the key to read.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - value or None
   */
  def getValueByKey(
    key: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[KVPair]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/kv/$key?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption.mapRight(_.flatMap(_.headOption)))

    sendRequest(request, token)
  }

  /**
   * GET /kv/:key?recurse recurse - Specifies to get all keys which have the specified prefix.
   * @param path
   *   - Specifies the path of the key to read.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - list of KV pairs
   */
  def getValuesByKeyPath(
    path: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[List[KVPair]]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/kv/$path?recurse&dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asKVPairListOption)

    sendRequest(request, token)
  }

  /**
   * GET /kv/:key?keys
   * @param path
   *   - Specifies the path of the key to read.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param separator
   *   - Specifies the string to use as a separator for recursive key lookups. This option is only used when paired with the keys parameter
   *     to limit the prefix of keys returned, only up to the given separator. This is specified as part of the URL as a query parameter.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - list of keys
   */
  def getKeyListByPath(
    path: String,
    dc: Option[String] = None,
    separator: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[List[String]]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/kv/$path?keys&dc=$dc&separator=$separator", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asStringListOption)

    sendRequest(request, token)
  }

  /**
   * GET /kv/:key?raw
   * @param key
   *   - Specifies the path of the key to read.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param consistencyMode
   *   - see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - raw value of specified key
   */
  def getRawValueByKey(
    key: String,
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[Option[String]]] = {
    val requestTemplate = basicRequest.get(addConsistencyMode(uri"$url/kv/$key?raw&dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.code.isSuccess) {
        Right(Some(str))
      } else if (meta.code.code == 404) { // If no key exists at the given path, a 404 is returned instead of a 200 response.
        Right(None)
      } else {
        Left[ConsulResponseError, Option[String]](HttpError(str, meta.code))
      }
    })

    sendRequest(request, token)
  }

  /**
   * PUT /kv/:key This endpoint updates the value of the specified key. If no key exists at the given path, the key will be created.
   * @param key
   *   - Specifies the path of the key to read.
   * @param value
   *   - The payload is arbitrary, and is loaded directly into Consul as supplied.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter. Using this across datacenters is not recommended.
   * @param flags
   *   - Specifies an unsigned value between 0 and pow(2, 64)-1. Clients can choose to use this however makes sense for their application.
   *     This is specified as part of the URL as a query parameter.
   * @param cas
   *   - Specifies to use a Check-And-Set operation. This is very useful as a building block for more complex synchronization primitives. If
   *     the index is 0, Consul will only put the key if it does not already exist. If the index is non-zero, the key is only set if the
   *     index matches the ModifyIndex of that key.
   * @param acquire
   *   - Supply a session ID to use in a lock acquisition operation. This is useful as it allows leader election to be built on top of
   *     Consul. If the lock is not held and the session is valid, this increments the LockIndex and sets the Session value of the key in
   *     addition to updating the key contents. A key does not need to exist to be acquired. If the lock is already held by the given
   *     session, then the LockIndex is not incremented but the key contents are updated. This lets the current lock holder update the key
   *     contents without having to give up the lock and reacquire it. Note that an update that does not include the acquire parameter will
   *     proceed normally even if another session has locked the key.
   * @param release
   *   - Supply a session ID to use in a release operation. This is useful when paired with ?acquire= as it allows clients to yield a lock.
   *     This will leave the LockIndex unmodified but will clear the associated Session of the key. The key must be held by this session to
   *     be unlocked.
   * @param token
   *   - consul token
   * @return
   *   - true if key was created
   */
  def createOrUpdate(
    key: String,
    value: String,
    dc: Option[String] = None,
    flags: Option[Int] = None,
    cas: Option[Int] = None,
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

  /**
   * DELETE /kv/:key
   * @param key
   *   - Specifies the path of the key to read.
   * @param cas
   *   - Specifies to use a Check-And-Set operation. This is very useful as a building block for more complex synchronization primitives. If
   *     the index is 0, Consul will only put the key if it does not already exist. If the index is non-zero, the key is only set if the
   *     index matches the ModifyIndex of that key.
   * @param token
   *   - consul token
   * @return
   *   - true if key was deleted
   */
  def deleteByKey(key: String, cas: Option[Int] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$key?cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }

  /**
   * DELETE /kv/:key?recurse recurse - Specifies to delete all keys which have the specified prefix.
   * @param path
   *   - Specifies the path of the key to delete.
   * @param cas
   *   - Specifies to use a Check-And-Set operation. This is very useful as a building block for more complex synchronization primitives. If
   *     the index is 0, Consul will only put the key if it does not already exist. If the index is non-zero, the key is only set if the
   *     index matches the ModifyIndex of that key.
   * @param token
   *   - consul token
   * @return
   *   - true if key was deleted
   */
  def deleteByKeyPath(path: String, cas: Option[Int] = None, token: Option[String] = None): F[Result[Boolean]] = {
    val requestTemplate = basicRequest.delete(uri"$url/kv/$path?recurse&cas=${cas.map(_.toString())}")
    val request = requestTemplate.copy(response = jsonDecoder.asBoolean)

    sendRequest(request, token)
  }
}
