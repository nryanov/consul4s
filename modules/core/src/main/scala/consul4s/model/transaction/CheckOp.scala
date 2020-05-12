package consul4s.model.transaction

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class CheckOp(val value: String) extends StringEnumEntry

object CheckOp extends StringEnum[CheckOp] {
  override def values: IndexedSeq[CheckOp] = findValues

  case object Get extends CheckOp("get")

  case object Set extends CheckOp("set")

  case object Cas extends CheckOp("cas")

  case object Delete extends CheckOp("delete")

  case object DeleteCas extends CheckOp("delete-cas")
}
