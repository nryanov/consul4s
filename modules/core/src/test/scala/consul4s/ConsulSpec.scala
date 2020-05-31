package consul4s

import consul4s.v1.ConsulClient
import sttp.client.{HttpURLConnectionBackend, Identity, NothingT}
import sttp.client.logging.slf4j.Slf4jCurlBackend

abstract class ConsulSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends BaseSpec {
  protected def createClient(consul: ConsulContainer): ConsulClient[Identity] = {
    val backend = Slf4jCurlBackend[Identity, Nothing, NothingT](HttpURLConnectionBackend())
    ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}", backend)
  }
}
