package consul4s.model.transaction

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class SessionOp(val value: String) extends StringEnumEntry

object SessionOp extends StringEnum[SessionOp] {
  override def values: IndexedSeq[SessionOp] = findValues

  case object Delete extends SessionOp("delete")
}
