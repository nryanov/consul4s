package consul4s.model

import enumeratum.values._

sealed abstract class CheckState(val value: String) extends StringEnumEntry

object CheckState extends StringEnum[CheckState] {
  override def values: IndexedSeq[CheckState] = findValues

  case object Passing extends CheckState("passing")

  case object Warning extends CheckState("warning")

  case object Critical extends CheckState("critical")
}
