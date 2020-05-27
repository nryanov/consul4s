package consul4s.v1.api

import consul4s.model.event.UserEvent
import sttp.client._

trait Event[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT	/event/fire/:name
   * @param name
   * @param payload
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param node
   * @param service
   * @param tag
   * @param token - consul token
   * @return
   */
  def fireEvent(
    name: String,
    payload: String,
    dc: Option[String] = None,
    node: Option[String] = None,
    service: Option[String] = None,
    tag: Option[String] = None,
    token: Option[String] = None
  ): F[Result[UserEvent]] = {
    val requestTemplate = basicRequest.put(uri"$url/event/fire/$name?dc=$dc&node=$node&service=$service&tag=$tag")
    val request = requestTemplate.copy(response = jsonDecoder.asUserEvent).body(payload)

    sendRequest(request, token)
  }

  /**
   * GET	/event/list
   * @param name
   * @param node
   * @param service
   * @param tag
   * @param token - consul token
   * @return
   */
  def listEvents(
    name: Option[String] = None,
    node: Option[String] = None,
    service: Option[String] = None,
    tag: Option[String] = None,
    token: Option[String] = None
  ): F[Result[List[UserEvent]]] = {
    val requestTemplate = basicRequest.get(uri"$url/event/list?&name=$name&node=$node&service=$service&tag=$tag")
    val request = requestTemplate.copy(response = jsonDecoder.asUserEventList)

    sendRequest(request, token)
  }

}
