package consul4s.model.query

import consul4s.model.health.ServiceEntry
import QueryResult._

final case class QueryResult(service: String, datacenter: String, dns: DNS, nodes: List[ServiceEntry])

object QueryResult {
  final case class DNS(ttl: String)
}
