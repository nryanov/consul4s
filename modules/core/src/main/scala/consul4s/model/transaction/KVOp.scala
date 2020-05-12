package consul4s.model.transaction

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class KVOp(val value: String) extends StringEnumEntry

object KVOp extends StringEnum[KVOp] {
  override def values: IndexedSeq[KVOp] = findValues

  case object Set extends KVOp("set")
  case object Delete extends KVOp("delete")
  case object DeleteCAS extends KVOp("delete-cas")
  case object DeleteTree extends KVOp("delete-tree")
  case object CAS extends KVOp("cas")
  case object Lock extends KVOp("lock")
  case object Unlock extends KVOp("unlock")
  case object Get extends KVOp("get")
  case object GetTree extends KVOp("get-tree")
  case object CheckSession extends KVOp("check-session")
  case object CheckIndex extends KVOp("check-index")
  case object CheckNotExists extends KVOp("check-not-exists")
}
