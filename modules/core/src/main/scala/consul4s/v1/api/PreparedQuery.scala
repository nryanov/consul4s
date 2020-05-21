package consul4s.v1.api

import consul4s.model.query.QueryResult
import sttp.client._

trait PreparedQuery[F[_]] { this: ConsulApi[F] =>
  // GET	/query/:uuid/execute
  def executeQuery(
    queryUUID: String,
    dc: Option[String] = None,
    near: Option[String] = None,
    limit: Option[Int] = None,
    connect: Boolean = false
  ): F[Result[Option[QueryResult]]] = {
    val requestTemplate = basicRequest.get(uri"$url/query/$queryUUID/execute?dc=$dc&near=$near&limit=$limit&connect=$connect")
    val request = requestTemplate.copy(response = jsonDecoder.asQueryResultOption)

    val response = sttpBackend.send(request)
    response
  }

}
