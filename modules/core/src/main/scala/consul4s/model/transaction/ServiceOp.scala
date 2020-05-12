package consul4s.model.transaction

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class ServiceOp(val value: String) extends StringEnumEntry

object ServiceOp extends StringEnum[ServiceOp] {
  override def values: IndexedSeq[ServiceOp] = findValues

  case object Get extends ServiceOp("get")

  case object Set extends ServiceOp("set")

  case object Cas extends ServiceOp("cas")

  case object Delete extends ServiceOp("delete")

  case object DeleteCas extends ServiceOp("delete-cas")
}
