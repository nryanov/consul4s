package consul4s.sprayJson.model

import consul4s.model.query._
import consul4s.model.query.QueryResult.DNS
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Query extends DefaultJsonProtocol { this: Health =>
  implicit val dnsFormat: RootJsonFormat[DNS] = jsonFormat1(DNS)

  implicit val queryResultFormat: RootJsonFormat[QueryResult] = jsonFormat4(QueryResult.apply)
}
