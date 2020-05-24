package consul4s.sprayJson.model

import consul4s.model.agent._
import spray.json._

trait Agent extends DefaultJsonProtocol { this: Health with Catalog with Common =>
  implicit val taggedAddressFormat: RootJsonFormat[TaggedAddress] = jsonFormat(TaggedAddress.apply, "Address", "Port")

  implicit val tokenFormat: RootJsonFormat[Token] = jsonFormat(Token, "Token")

  implicit val weightsFormat: RootJsonFormat[Weights] = jsonFormat(Weights.apply, "Passing", "Warning")

  implicit val aggregatedServiceStatusFormat: RootJsonFormat[AggregatedServiceStatus] =
    jsonFormat(AggregatedServiceStatus.apply, "AggregatedStatus", "Service", "Checks")

  implicit val checkUpdateFormat: RootJsonFormat[CheckUpdate] = jsonFormat(CheckUpdate.apply, "Status", "Output")

  implicit val memberInfoFormat: RootJsonFormat[MemberInfo] = jsonFormat(
    MemberInfo.apply,
    "Name",
    "Addr",
    "Port",
    "Tags",
    "Status",
    "ProtocolMin",
    "ProtocolMax",
    "ProtocolCur",
    "DelegateMin",
    "DelegateMax",
    "DelegateCur"
  )

  implicit val scriptCheckFormat: RootJsonFormat[ScriptCheck] = jsonFormat(
    ScriptCheck.apply,
    "Name",
    "Args",
    "Timeout",
    "Interval",
    "ID",
    "ServiceID",
    "Status",
    "Notes",
    "DeregisterCriticalServiceAfter"
  )

  implicit val httpCheckFormat: RootJsonFormat[HttpCheck] = jsonFormat(
    HttpCheck.apply,
    "Name",
    "HTTP",
    "TLSSkipVerify",
    "Interval",
    "Timeout",
    "Header",
    "Method",
    "Body",
    "ID",
    "ServiceID",
    "Status",
    "Notes",
    "SuccessBeforePassing",
    "FailuresBeforeCritical",
    "DeregisterCriticalServiceAfter"
  )

  implicit val tcpCheckFormat: RootJsonFormat[TCPCheck] = jsonFormat(
    TCPCheck.apply,
    "Name",
    "TCP",
    "Interval",
    "Timeout",
    "ID",
    "ServiceID",
    "Status",
    "Notes",
    "SuccessBeforePassing",
    "FailuresBeforeCritical",
    "DeregisterCriticalServiceAfter"
  )

  implicit val ttlCheckFormat: RootJsonFormat[TTLCheck] =
    jsonFormat(TTLCheck.apply, "Name", "TTL", "ID", "ServiceID", "Status", "Notes", "DeregisterCriticalServiceAfter")

  implicit val dockerCheckFormat: RootJsonFormat[DockerCheck] = jsonFormat(
    DockerCheck.apply,
    "Name",
    "DockerContainerId",
    "Shell",
    "Args",
    "Interval",
    "ID",
    "ServiceID",
    "Status",
    "Notes",
    "SuccessBeforePassing",
    "FailuresBeforeCritical",
    "DeregisterCriticalServiceAfter"
  )

  implicit val gRpcCheckFormat: RootJsonFormat[GRpcCheck] = jsonFormat(
    GRpcCheck.apply,
    "Name",
    "GRPC",
    "GRPCUseTLS",
    "Interval",
    "ID",
    "ServiceID",
    "Status",
    "Notes",
    "SuccessBeforePassing",
    "FailuresBeforeCritical",
    "DeregisterCriticalServiceAfter"
  )

  implicit val aliasCheckFormat: RootJsonFormat[AliasCheck] = jsonFormat(AliasCheck.apply, "ID", "AliasNode", "AliasService")

  implicit val checkFormat = new RootJsonFormat[Check] {
    def write(obj: Check): JsValue =
      JsObject(
        (obj match {
          case v: ScriptCheck => v.toJson
          case v: HttpCheck   => v.toJson
          case v: TCPCheck    => v.toJson
          case v: TTLCheck    => v.toJson
          case v: DockerCheck => v.toJson
          case v: GRpcCheck   => v.toJson
          case v: AliasCheck  => v.toJson
        }).asJsObject.fields
      )

    def read(json: JsValue): Check = throw new UnsupportedOperationException("Not supported")
  }

  implicit val newServiceFormat: RootJsonFormat[NewService] = jsonFormat(
    NewService.apply,
    "Name",
    "ID",
    "Tags",
    "Address",
    "TaggedAddresses",
    "Meta",
    "Port",
    "Check",
    "Checks",
    "EnableTagOverride",
    "Weights"
  )

  implicit val serviceFormat: RootJsonFormat[Service] =
    jsonFormat(Service.apply, "Service", "ID", "Tags", "Address", "TaggedAddresses", "Meta", "Port", "EnableTagOverride", "Weights")

  implicit object UpstreamDestTypeFormat extends RootJsonFormat[UpstreamDestType] {
    override def write(obj: UpstreamDestType): JsValue = JsString(obj.value)

    override def read(json: JsValue): UpstreamDestType = json match {
      case JsString(value) => UpstreamDestType.withValue(value)
      case _               => deserializationError("UpstreamDestType expected")
    }
  }
}
