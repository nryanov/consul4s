package consul4s.json4s.model

import consul4s.model.query._
import consul4s.model.query.QueryResult._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Query {
  val queryResultFormat = FieldSerializer[QueryResult](
    Map(),
    renameFrom("Service", "service")
      .orElse(renameFrom("Datacenter", "datacenter"))
      .orElse(renameFrom("DNS", "dns"))
      .orElse(renameFrom("Nodes", "nodes"))
  )
  val dnsFormat = FieldSerializer[DNS](Map(), renameFrom("TTL", "ttl"))

  val queryFieldAllSerializers = List(dnsFormat, queryResultFormat)
}
