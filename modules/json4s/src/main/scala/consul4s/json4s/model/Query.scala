package consul4s.json4s.model

import consul4s.model.query._
import consul4s.model.query.QueryResult._
import org.json4s.FieldSerializer

trait Query {
  val queryResultFormat: FieldSerializer[QueryResult] = FieldSerializer[QueryResult]()
  val dnsFormat: FieldSerializer[DNS] = FieldSerializer[DNS]()

  val queryFieldAllSerializers = List(dnsFormat, queryResultFormat)
}
