package consul4s.model.transaction

final case class TxnResponse(results: List[TxnResult], errors: List[TxnError])
