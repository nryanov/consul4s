package consul4s.model.transaction

import consul4s.model.kv.KVPair

final case class KVTxnResponse(results: List[KVPair], errors: List[TxnError])
