package consul4s.circe.model

import consul4s.model.query.{DNS, QueryResult}
import io.circe._
import io.circe.generic.semiauto._

trait Query { this: Health =>
  implicit val dnsDecoder: Decoder[DNS] = deriveDecoder[DNS]
  implicit val queryResultDecoder: Decoder[QueryResult] = deriveDecoder[QueryResult]

}
