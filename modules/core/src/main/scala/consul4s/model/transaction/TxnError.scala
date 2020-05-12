package consul4s.model.transaction

final case class TxnError(opIndex: Int, what: String)
