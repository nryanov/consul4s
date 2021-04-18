package consul4s.sprayJson.model

import consul4s.model.agent._
import spray.json._

trait Agent extends DefaultJsonProtocol { this: Health with Catalog with Common =>
  implicit val taggedAddressFormat: RootJsonFormat[TaggedAddress] = jsonFormat2(TaggedAddress.apply)

  implicit val tokenFormat: RootJsonFormat[Token] = jsonFormat1(Token)

  implicit val weightsFormat: RootJsonFormat[Weights] = jsonFormat2(Weights.apply)

  implicit val checkUpdateFormat: RootJsonFormat[CheckUpdate] = jsonFormat2(CheckUpdate.apply)

  implicit val memberInfoFormat: RootJsonFormat[MemberInfo] = jsonFormat11(MemberInfo.apply)

  implicit val scriptCheckFormat: RootJsonFormat[ScriptCheck] = jsonFormat9(ScriptCheck.apply)

  implicit val httpCheckFormat: RootJsonFormat[HttpCheck] = jsonFormat15(HttpCheck.apply)

  implicit val tcpCheckFormat: RootJsonFormat[TCPCheck] = jsonFormat11(TCPCheck.apply)

  implicit val ttlCheckFormat: RootJsonFormat[TTLCheck] = jsonFormat7(TTLCheck.apply)

  implicit val dockerCheckFormat: RootJsonFormat[DockerCheck] = jsonFormat12(DockerCheck.apply)

  implicit val gRpcCheckFormat: RootJsonFormat[GRpcCheck] = jsonFormat11(GRpcCheck.apply)

  implicit val aliasCheckFormat: RootJsonFormat[AliasCheck] = jsonFormat3(AliasCheck.apply)

  implicit val checkFormat: RootJsonFormat[Check] = new RootJsonFormat[Check] {
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

  implicit val serviceScriptCheckFormat: RootJsonFormat[ServiceScriptCheck] = jsonFormat9(ServiceScriptCheck.apply)

  implicit val serviceHttpCheckFormat: RootJsonFormat[ServiceHttpCheck] = jsonFormat15(ServiceHttpCheck.apply)

  implicit val serviceTcpCheckFormat: RootJsonFormat[ServiceTCPCheck] = jsonFormat11(ServiceTCPCheck.apply)

  implicit val serviceTtlCheckFormat: RootJsonFormat[ServiceTTLCheck] = jsonFormat7(ServiceTTLCheck.apply)

  implicit val serviceDockerCheckFormat: RootJsonFormat[ServiceDockerCheck] = jsonFormat12(ServiceDockerCheck.apply)

  implicit val serviceGRpcCheckFormat: RootJsonFormat[ServiceGRpcCheck] = jsonFormat11(ServiceGRpcCheck.apply)

  implicit val serviceAliasCheckFormat: RootJsonFormat[ServiceAliasCheck] =
    jsonFormat3(ServiceAliasCheck.apply)

  implicit val serviceCheckFormat: RootJsonFormat[ServiceCheck] = new RootJsonFormat[ServiceCheck] {
    def write(obj: ServiceCheck): JsValue =
      JsObject(
        (obj match {
          case v: ServiceScriptCheck => v.toJson
          case v: ServiceHttpCheck   => v.toJson
          case v: ServiceTCPCheck    => v.toJson
          case v: ServiceTTLCheck    => v.toJson
          case v: ServiceDockerCheck => v.toJson
          case v: ServiceGRpcCheck   => v.toJson
          case v: ServiceAliasCheck  => v.toJson
        }).asJsObject.fields
      )

    def read(json: JsValue): ServiceCheck = throw new UnsupportedOperationException("Not supported")
  }

  implicit val newServiceFormat: RootJsonFormat[NewService] = jsonFormat11(NewService.apply)

  implicit val serviceFormat: RootJsonFormat[Service] = jsonFormat9(Service.apply)

  implicit val aggregatedServiceStatusFormat: RootJsonFormat[AggregatedServiceStatus] =
    rootFormat(lazyFormat(jsonFormat3(AggregatedServiceStatus.apply)))

  implicit object UpstreamDestTypeFormat extends RootJsonFormat[UpstreamDestType] {
    override def write(obj: UpstreamDestType): JsValue = JsString(obj.value)

    override def read(json: JsValue): UpstreamDestType = json match {
      case JsString(value) => UpstreamDestType.withValue(value)
      case _               => deserializationError("UpstreamDestType expected")
    }
  }
}
