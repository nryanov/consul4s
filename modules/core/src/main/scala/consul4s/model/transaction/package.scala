package consul4s.model

import enumeratum.values.{StringEnum, StringEnumEntry}

package object transaction {
  sealed abstract class CheckOp(val value: String) extends StringEnumEntry

  object CheckOp extends StringEnum[CheckOp] {
    val values = findValues

    case object Get extends CheckOp("get")

    case object Set extends CheckOp("set")

    case object Cas extends CheckOp("cas")

    case object Delete extends CheckOp("delete")

    case object DeleteCas extends CheckOp("delete-cas")
  }

  sealed abstract class KVOp(val value: String) extends StringEnumEntry

  object KVOp extends StringEnum[KVOp] {
    val values = findValues

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

  sealed abstract class NodeOp(val value: String) extends StringEnumEntry

  object NodeOp extends StringEnum[NodeOp] {
    val values = findValues

    case object Get extends NodeOp("get")

    case object Set extends NodeOp("set")

    case object Cas extends NodeOp("cas")

    case object Delete extends NodeOp("delete")

    case object DeleteCas extends NodeOp("delete-cas")
  }

  sealed abstract class ServiceOp(val value: String) extends StringEnumEntry

  object ServiceOp extends StringEnum[ServiceOp] {
    val values = findValues

    case object Get extends ServiceOp("get")

    case object Set extends ServiceOp("set")

    case object Cas extends ServiceOp("cas")

    case object Delete extends ServiceOp("delete")

    case object DeleteCas extends ServiceOp("delete-cas")
  }
}
