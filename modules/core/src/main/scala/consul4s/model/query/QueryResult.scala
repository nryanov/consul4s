package consul4s.model.query

import consul4s.model.health.ServiceEntry
import QueryResult._

final case class QueryResult(Service: String, Datacenter: String, DNS: DNS, Nodes: List[ServiceEntry])

object QueryResult {
  final case class DNS(TTL: String)
}
