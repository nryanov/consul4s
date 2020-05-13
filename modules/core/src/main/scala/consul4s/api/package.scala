package consul4s

import sttp.client.{NothingT, SttpBackend}

package object api {
  abstract class ConsulApi[F[_]](protected val url: String, protected val sttpBackend: SttpBackend[F, Nothing, NothingT])(
    implicit protected val jsonDecoder: JsonDecoder,
    jsonEncoder: JsonEncoder
  ) extends KVStore[F]
      with Status[F]
      with Health[F]
      with Catalog[F]
      with Agent[F]
}
