import sttp.client3.ResponseException

package object consul4s {
  type ConsulResponseError = ResponseException[String, Exception]
}
