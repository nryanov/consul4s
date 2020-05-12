package consul4s.model.transaction

final case class KVTxnOp(
  verb: KVOp,
  key: String,
  value: Array[Byte],
  flags: Long,
  index: Long,
  session: String,
  namespace: Option[String] = None
)
