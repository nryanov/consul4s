package consul4s.api

import consul4s.model.event.UserEvent
import sttp.client._

trait Event[F[_]] { this: ConsulApi[F] =>
  // PUT	/event/fire/:name
  def fireEvent(
    name: String,
    payload: String,
    dc: Option[String] = None,
    node: Option[String] = None,
    service: Option[String] = None,
    tag: Option[String] = None
  ): F[Result[UserEvent]] = {
    val requestTemplate = basicRequest.put(uri"$url/event/fire/$name?dc=$dc&node=$node&service=$service&tag=$tag")
    val request = requestTemplate.copy(response = jsonDecoder.asUserEvent).body(payload)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/event/list
  def listEvents(
    name: Option[String] = None,
    node: Option[String] = None,
    service: Option[String] = None,
    tag: Option[String] = None
  ): F[Result[List[UserEvent]]] = {
    val requestTemplate = basicRequest.get(uri"$url/event/list?&name=$name&node=$node&service=$service&tag=$tag")
    val request = requestTemplate.copy(response = jsonDecoder.asUserEventList)

    val response = sttpBackend.send(request)
    response
  }

}
