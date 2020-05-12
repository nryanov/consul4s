package consul4s.model.transaction

import consul4s.model.catalog.{CatalogService, Node}
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair

final case class TxnResult(kv: KVPair, node: Node, service: CatalogService, check: HealthCheck)
