package consul4s.v1

import consul4s.{JsonDecoder, JsonEncoder}
import sttp.client.{HttpError, Identity, NothingT, RequestT, Response, ResponseAs, ResponseError, SttpBackend, asStringAlways}
import sttp.model.{Header, Uri}
import sttp.model.Uri.QuerySegment.Value

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

    protected final def asResultUnit: ResponseAs[Either[ResponseError[Exception], Unit], Nothing] = asStringAlways.mapWithMetadata {
      (str, meta) =>
        if (meta.isSuccess) {
          Right(())
        } else {
          Left[ResponseError[Exception], Unit](HttpError(str))
        }
    }

    protected final def sendRequest[A](
      request: RequestT[Identity, Either[ResponseError[Exception], A], Nothing],
      token: Option[String]
    ): F[Result[A]] =
      token match {
        case Some(value) => sttpBackend.send(addTokenHeader(request, value))
        case None        => sttpBackend.send(request)
      }

    protected final def addConsistencyMode(
      uri: Uri,
      consistencyMode: ConsistencyMode
    ): Uri =
      consistencyMode match {
        case ConsistencyMode.Default    => uri
        case ConsistencyMode.Consistent => uri.querySegment(Value(consistencyMode.value))
        case ConsistencyMode.Stale      => uri.querySegment(Value(consistencyMode.value))
      }

    private def addTokenHeader[A](
      request: RequestT[Identity, Either[ResponseError[Exception], A], Nothing],
      token: String
    ): RequestT[Identity, Either[ResponseError[Exception], A], Nothing] =
      request.copy(headers = request.headers ++ Seq(Header("X-Consul-Token", token)))
  }
}
