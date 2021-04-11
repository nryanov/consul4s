package consul4s.zio.json.model

import consul4s.model.health.ServiceEntry
import consul4s.model.query.QueryResult
import consul4s.model.query.QueryResult.DNS
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Query { this: Health =>
  private[zio] case class DNSRepr(ttl: String)

  implicit val dnsCodec: JsonCodec[DNS] = ConverterMacros.derive[DNSRepr, DNS]

  private[zio] case class QueryResultRepr(service: String, datacenter: String, dns: DNS, nodes: List[ServiceEntry])

  implicit val queryResultCodec: JsonCodec[QueryResult] = ConverterMacros.derive[QueryResultRepr, QueryResult]
}
