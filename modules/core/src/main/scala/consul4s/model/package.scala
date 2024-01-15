package consul4s

package object model {

  sealed abstract class CheckStatus(val value: String)

  object CheckStatus {
    def unapply(v: String): Option[CheckStatus] = v match {
      case "any"         => Some(Any)
      case "passing"     => Some(Passing)
      case "warning"     => Some(Warning)
      case "critical"    => Some(Critical)
      case "maintenance" => Some(Maintenance)
      case _             => None
    }

    case object Any extends CheckStatus("any")

    case object Passing extends CheckStatus("passing")

    case object Warning extends CheckStatus("warning")

    case object Critical extends CheckStatus("critical")

    case object Maintenance extends CheckStatus("maintenance")
  }

  sealed abstract class ServiceKind(val value: String)

  object ServiceKind {
    def unapply(v: String): Option[ServiceKind] = v match {
      case ""                => Some(Typical)
      case "connect-proxy"   => Some(ConnectProxy)
      case "mesh-gateway"    => Some(MeshGateway)
      case "ingress-gateway" => Some(IngressGateway)
      case _                 => None
    }

    case object Typical extends ServiceKind("")

    case object ConnectProxy extends ServiceKind("connect-proxy")

    case object MeshGateway extends ServiceKind("mesh-gateway")

    case object IngressGateway extends ServiceKind("ingress-gateway")
  }

  sealed abstract class SessionBehavior(val value: String)

  object SessionBehavior {
    def unapply(v: String): Option[SessionBehavior] = v match {
      case "release" => Some(Release)
      case "delete"  => Some(Delete)
      case _         => None
    }

    case object Release extends SessionBehavior("release")

    case object Delete extends SessionBehavior("delete")
  }
}
