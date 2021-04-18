package consul4s.model.transaction

import consul4s.model.agent.Service
import consul4s.model.catalog.Node
import consul4s.model.health.HealthCheck
import TxResults._
import consul4s.model.kv.KVPair

final case class TxResults(Results: Option[List[TxResult]], Errors: Option[List[TxError]])

object TxResults {
  final case class TxResult(KV: Option[KVPair], Node: Option[Node], Service: Option[Service], Check: Option[HealthCheck])

  final case class TxError(OpIndex: Long, What: String)
}
