package consul4s.model

import enumeratum.values._

sealed abstract class State(val value: String) extends StringEnumEntry

object State extends StringEnum[State] {
  override def values: IndexedSeq[State] = findValues

  case object Any extends State("any")

  case object Passing extends State("passing")

  case object Warning extends State("warning")

  case object Critical extends State("critical")
}
//const (
//// HealthAny is special, and is used as a wild card,
//// not as a specific state.
//HealthAny      = "any"
//HealthPassing  = "passing"
//HealthWarning  = "warning"
//HealthCritical = "critical"
//HealthMaint    = "maintenance"
//)
