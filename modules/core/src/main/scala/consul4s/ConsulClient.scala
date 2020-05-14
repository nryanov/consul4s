package consul4s

import consul4s.api._
import sttp.client.{SttpBackend, _}

final class ConsulClient[F[_]](url: String, sttpBackend: SttpBackend[F, Nothing, NothingT])(
  implicit jsonDecoder: JsonDecoder,
  jsonEncoder: JsonEncoder
) extends ConsulApi[F](url, sttpBackend)

object ConsulClient {
  def apply[F[_]](
    url: String,
    sttpBackend: SttpBackend[F, Nothing, NothingT]
  )(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder): ConsulClient[F] =
    new ConsulClient(url, sttpBackend)
}
