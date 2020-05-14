package consul4s.model

import enumeratum.values._

sealed abstract class CheckStatus(val value: String) extends StringEnumEntry

object CheckStatus extends StringEnum[CheckStatus] {
  override def values: IndexedSeq[CheckStatus] = findValues

  case object Any extends CheckStatus("any")

  case object Passing extends CheckStatus("passing")

  case object Warning extends CheckStatus("warning")

  case object Critical extends CheckStatus("critical")

  case object Maintenance extends CheckStatus("maintenance")
}
