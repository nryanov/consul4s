package consul4s

import consul4s.api._
import sttp.client.{SttpBackend, _}
import sttp.client.logging.slf4j.Slf4jCurlBackend

final class ConsulClient[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(
  implicit jsonDecoder: JsonDecoder,
  jsonEncoder: JsonEncoder
) extends ConsulApi[F](url, Slf4jCurlBackend[F, Nothing, NothingT](sttpBackend))

object ConsulClient {
  def apply[F[_]](
    url: String,
    sttpBackend: SttpBackend[F, Nothing, NothingT]
  )(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder): ConsulClient[F] =
    new ConsulClient(url, sttpBackend)
}
