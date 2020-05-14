package consul4s

import sttp.client.{HttpURLConnectionBackend, Identity}

abstract class ConsulSpec(implicit jsonDecoder: JsonDecoder, jsonEncoder: JsonEncoder) extends BaseSpec {
  protected def createClient(consul: ConsulContainer): ConsulClient[Identity] = {
    val backend = HttpURLConnectionBackend()
    ConsulClient(s"http://${consul.containerIpAddress}:${consul.mappedPort(8500)}/v1", backend)
  }
}
