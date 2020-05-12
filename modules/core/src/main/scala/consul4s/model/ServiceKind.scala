package consul4s.model

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class ServiceKind(val value: String) extends StringEnumEntry

object ServiceKind extends StringEnum[ServiceKind] {
  override def values: IndexedSeq[ServiceKind] = findValues

  case object Typical extends ServiceKind("")

  case object ConnectProxy extends ServiceKind("connect-proxy")

  case object MeshGateway extends ServiceKind("mesh-gateway")

  case object IngressGateway extends ServiceKind("ingress-gateway")
}
