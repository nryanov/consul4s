package consul4s

import enumeratum.values._

package object model {

  sealed abstract class CheckStatus(val value: String) extends StringEnumEntry

  object CheckStatus extends StringEnum[CheckStatus] {
    val values = findValues

    case object Any extends CheckStatus("any")

    case object Passing extends CheckStatus("passing")

    case object Warning extends CheckStatus("warning")

    case object Critical extends CheckStatus("critical")

    case object Maintenance extends CheckStatus("maintenance")
  }

  sealed abstract class ServiceKind(val value: String) extends StringEnumEntry

  object ServiceKind extends StringEnum[ServiceKind] {
    val values = findValues

    case object Typical extends ServiceKind("")

    case object ConnectProxy extends ServiceKind("connect-proxy")

    case object MeshGateway extends ServiceKind("mesh-gateway")

    case object IngressGateway extends ServiceKind("ingress-gateway")
  }

  sealed abstract class SessionBehavior(val value: String) extends StringEnumEntry

  object SessionBehavior extends StringEnum[SessionBehavior] {
    val values = findValues

    case object Release extends SessionBehavior("release")

    case object Delete extends SessionBehavior("delete")
  }
}
