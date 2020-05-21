package consul4s.model.transaction

import consul4s.model.agent.Service
import consul4s.model.catalog.Node
import consul4s.model.health.HealthCheck
import TxResults._
import consul4s.model.kv.KVPair

final case class TxResults(results: Option[List[TxResult]], errors: Option[List[TxError]])

object TxResults {
  final case class TxResult(kv: Option[KVPair], node: Option[Node], service: Option[Service], check: Option[HealthCheck])

  final case class TxError(opIndex: Long, what: String)
}
