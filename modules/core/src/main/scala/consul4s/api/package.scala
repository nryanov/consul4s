package consul4s

import sttp.client.{HttpError, NothingT, Response, ResponseAs, ResponseError, SttpBackend, asStringAlways}

package object api {
  abstract class ConsulApi[F[_]](protected val url: String, protected val sttpBackend: SttpBackend[F, Nothing, NothingT])(
    implicit protected val jsonDecoder: JsonDecoder,
    protected val jsonEncoder: JsonEncoder
  ) extends KVStore[F]
      with Status[F]
      with Health[F]
      with Catalog[F]
      with Agent[F]
      with Event[F]
      with Session[F]
      with Coordinate[F]
      with PreparedQuery[F]
      with Transaction[F] {
    type Result[A] = Response[Either[ResponseError[Exception], A]]

    protected def asResultUnit: ResponseAs[Either[ResponseError[Exception], Unit], Nothing] = asStringAlways.mapWithMetadata {
      (str, meta) =>
        if (meta.isSuccess) {
          Right(())
        } else {
          Left[ResponseError[Exception], Unit](HttpError(str))
        }
    }
  }
}
