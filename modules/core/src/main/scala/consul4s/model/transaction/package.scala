package consul4s.model

package object transaction {
  sealed abstract class CheckOp(val value: String)

  object CheckOp {
    def unapply(v: String): Option[CheckOp] = v match {
      case "get"        => Some(Get)
      case "set"        => Some(Set)
      case "cas"        => Some(Cas)
      case "delete"     => Some(Delete)
      case "delete-cas" => Some(DeleteCas)
      case _            => None
    }

    case object Get extends CheckOp("get")

    case object Set extends CheckOp("set")

    case object Cas extends CheckOp("cas")

    case object Delete extends CheckOp("delete")

    case object DeleteCas extends CheckOp("delete-cas")
  }

  sealed abstract class KVOp(val value: String)

  object KVOp {
    def unapply(v: String): Option[KVOp] = v match {
      case "set"              => Some(Set)
      case "delete"           => Some(Delete)
      case "delete-cas"       => Some(DeleteCAS)
      case "delete-tree"      => Some(DeleteTree)
      case "cas"              => Some(CAS)
      case "lock"             => Some(Lock)
      case "unlock"           => Some(Unlock)
      case "get"              => Some(Get)
      case "get-tree"         => Some(GetTree)
      case "check-session"    => Some(CheckSession)
      case "check-index"      => Some(CheckIndex)
      case "check-not-exists" => Some(CheckNotExists)
      case _                  => None
    }

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

  sealed abstract class NodeOp(val value: String)

  object NodeOp {
    def unapply(v: String): Option[NodeOp] = v match {
      case "get"        => Some(Get)
      case "set"        => Some(Set)
      case "cas"        => Some(Cas)
      case "delete"     => Some(Delete)
      case "delete-cas" => Some(DeleteCas)
      case _            => None
    }

    case object Get extends NodeOp("get")

    case object Set extends NodeOp("set")

    case object Cas extends NodeOp("cas")

    case object Delete extends NodeOp("delete")

    case object DeleteCas extends NodeOp("delete-cas")
  }

  sealed abstract class ServiceOp(val value: String)

  object ServiceOp {
    def unapply(v: String): Option[ServiceOp] = v match {
      case "get"        => Some(Get)
      case "set"        => Some(Set)
      case "cas"        => Some(Cas)
      case "delete"     => Some(Delete)
      case "delete-cas" => Some(DeleteCas)
      case _            => None
    }

    case object Get extends ServiceOp("get")

    case object Set extends ServiceOp("set")

    case object Cas extends ServiceOp("cas")

    case object Delete extends ServiceOp("delete")

    case object DeleteCas extends ServiceOp("delete-cas")
  }
}
