package consul4s.v1

import consul4s.ConsulResponseError
import consul4s.{BackgroundRefreshCache, CacheMode, ConsistencyMode, JsonDecoder, JsonEncoder, NoCache, SimpleCache}
import sttp.client3.{HttpError, Identity, RequestT, Response, ResponseAs, SttpBackend, asStringAlways}
import sttp.model.{Header, Uri}
import sttp.model.Uri.QuerySegment.Value

package object api {
  abstract class ConsulApi[F[_]](protected val url: String, protected val sttpBackend: SttpBackend[F, Any])(implicit
    protected val jsonDecoder: JsonDecoder,
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
    type Result[A] = Response[Either[ConsulResponseError, A]]

    protected final def asResultUnit: ResponseAs[Either[ConsulResponseError, Unit], Any] = asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.isSuccess) {
        Right(())
      } else {
        Left[ConsulResponseError, Unit](HttpError(str, meta.code))
      }
    }

    protected final def saveSendRequest[A](
      request: RequestT[Identity, Either[ConsulResponseError, A], Any],
      bodyF: => String,
      token: Option[String]
    ): F[Result[A]] =
      sttpBackend.responseMonad.flatMap(makeBody(bodyF))(body => sendRequest(request.body(body), token))

    protected final def sendRequest[A](
      request: RequestT[Identity, Either[ConsulResponseError, A], Any],
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

    protected final def addCacheMode[A](
      request: RequestT[Identity, Either[ConsulResponseError, A], Any],
      cacheMode: CacheMode
    ): RequestT[Identity, Either[ConsulResponseError, A], Any] = cacheMode match {
      case NoCache                         => request
      case SimpleCache(cacheControlHeader) =>
        cacheControlHeader match {
          case Some(value) =>
            val newUri: Identity[Uri] = request.uri.querySegment(Value("cached"))
            val newHeaders = request.headers ++ Seq(Header("Cache-Control", value))
            request.copy(uri = newUri, headers = newHeaders)
          case None =>
            val newUri: Identity[Uri] = request.uri.querySegment(Value("cached"))
            request.copy(uri = newUri)
        }
      case BackgroundRefreshCache =>
        val newUri: Identity[Uri] = request.uri.querySegment(Value("cached"))
        request.copy(uri = newUri)
    }

    private def addTokenHeader[A](
      request: RequestT[Identity, Either[ConsulResponseError, A], Any],
      token: String
    ): RequestT[Identity, Either[ConsulResponseError, A], Any] =
      request.copy(headers = request.headers ++ Seq(Header("X-Consul-Token", token)))

    private def makeBody(f: => String): F[String] = sttpBackend.responseMonad.eval(f)
  }
}
