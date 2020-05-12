package consul4s.model.transaction

import consul4s.model.kv.KVPair

final case class TxnOp(kv: KVPair, node: NodeTxnOp, service: ServiceTxnOp, check: CheckTxnOp)
