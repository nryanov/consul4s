package consul4s.model

import enumeratum.values._

sealed abstract class SessionBehavior(val value: String) extends StringEnumEntry

object SessionBehavior extends StringEnum[SessionBehavior] {
  override def values: IndexedSeq[SessionBehavior] = findValues

  case object Release extends SessionBehavior("release")

  case object Delete extends SessionBehavior("delete")
}
