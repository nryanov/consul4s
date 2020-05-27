package consul4s.v1

import consul4s.v1.api.ConsulApi
import consul4s.{JsonDecoder, JsonEncoder}
import sttp.client.logging.slf4j.Slf4jCurlBackend
import sttp.client.{SttpBackend, _}

final class ConsulClient[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(
  implicit jsonDecoder: JsonDecoder,
  jsonEncoder: JsonEncoder
) extends ConsulApi[F](url + "/v1", Slf4jCurlBackend[F, Nothing, NothingT](sttpBackend))

object ConsulClient {
  private val DEFAULT_ADDRESS = "http://localhost:8500"

  def apply[F[_]](
    url: String,
    sttpBackend: SttpBackend[F, Nothing, NothingT]
  )(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder): ConsulClient[F] =
    new ConsulClient(url, sttpBackend)

  def apply[F[_]](
    sttpBackend: SttpBackend[F, Nothing, NothingT]
  )(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder): ConsulClient[F] =
    new ConsulClient(DEFAULT_ADDRESS, sttpBackend)(jsonDecoder, jsonEncoder)
}
