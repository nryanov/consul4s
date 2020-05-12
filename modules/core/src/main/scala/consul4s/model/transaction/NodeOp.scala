package consul4s.model.transaction

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class NodeOp(val value: String) extends StringEnumEntry

object NodeOp extends StringEnum[NodeOp] {
  override def values: IndexedSeq[NodeOp] = findValues

  case object Get extends NodeOp("get")

  case object Set extends NodeOp("set")

  case object Cas extends NodeOp("cas")

  case object Delete extends NodeOp("delete")

  case object DeleteCas extends NodeOp("delete-cas")
}
