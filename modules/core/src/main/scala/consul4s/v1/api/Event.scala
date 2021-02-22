package consul4s.v1.api

import consul4s.model.event.UserEvent
import sttp.client3._

trait Event[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT	/event/fire/:name
   * This endpoint triggers a new user event.
   * @param name - Specifies the name of the event to fire. This is specified as part of the URL.
   * This name must not start with an underscore, since those are reserved for Consul internally.
   * @param payload
   * @param dc - Specifies the datacenter to query. This will default to the datacenter of the agent being queried.
   * This is specified as part of the URL as a query parameter. Using this across datacenters is not recommended.
   * @param node - Specifies a regular expression to filter by node name.
   * This is specified as part of the URL as a query parameter.
   * @param service - Specifies a regular expression to filter by service name.
   * This is specified as part of the URL as a query parameter.
   * @param tag - Specifies a regular expression to filter by tag. This is specified as part of the URL as a query parameter.
   * @param token - consul token
   * @return - event info
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
   * This endpoint returns the most recent events (up to 256) known by the agent.
   * @param name - filter events by name
   * @param node - Specifies a regular expression to filter by node name.
   * This is specified as part of the URL as a query parameter.
   * @param service - Specifies a regular expression to filter by service name.
   * This is specified as part of the URL as a query parameter.
   * @param tag - Specifies a regular expression to filter by tag. This is specified as part of the URL as a query parameter.
   * @param token - consul token
   * @return - list of events
   */
  def getEvents(
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
