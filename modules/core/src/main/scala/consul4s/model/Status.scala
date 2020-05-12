package consul4s.model

import enumeratum.values._

sealed abstract class Status(val value: String) extends StringEnumEntry

object Status extends StringEnum[Status] {
  override def values: IndexedSeq[Status] = findValues

  case object Any extends Status("any")

  case object Passing extends Status("passing")

  case object Warning extends Status("warning")

  case object Critical extends Status("critical")

  case object Maintenance extends Status("maintenance")
}
