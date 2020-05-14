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
  ): F[Response[UserEvent]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nodeParam = node.map(v => s"node=$v").getOrElse("")
    val serviceParam = service.map(v => s"service=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")

    val params = Map(
      "dc" -> dcParam,
      "node" -> nodeParam,
      "service" -> serviceParam,
      "tag" -> tagParam
    )

    val requestTemplate = basicRequest.put(uri"$url/event/fire/$name".params(params))
    val request = requestTemplate.copy(response = jsonDecoder.asUserEventUnsafe).body(payload)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/event/list
  def listEvents(
    name: Option[String] = None,
    node: Option[String] = None,
    service: Option[String] = None,
    tag: Option[String] = None
  ): F[Response[List[UserEvent]]] = {
    val nameParam = name.map(v => s"name=$v").getOrElse("")
    val nodeParam = node.map(v => s"node=$v").getOrElse("")
    val serviceParam = service.map(v => s"service=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")

    val params = Map(
      "name" -> nameParam,
      "node" -> nodeParam,
      "service" -> serviceParam,
      "tag" -> tagParam
    )

    val requestTemplate = basicRequest.get(uri"$url/event/list".params(params))
    val request = requestTemplate.copy(response = jsonDecoder.asUserEventListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

}
