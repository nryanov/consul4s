package consul4s

import consul4s.v1.ConsulClient
import sttp.client3.{HttpURLConnectionBackend, Identity}
import sttp.client3.logging.slf4j.Slf4jLoggingBackend

abstract class ConsulSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends BaseSpec {
  protected def createClient(consul: ConsulContainer): ConsulClient[Identity] = {
    val backend = Slf4jLoggingBackend[Identity, Any](HttpURLConnectionBackend())
    ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}", backend)
  }
}
