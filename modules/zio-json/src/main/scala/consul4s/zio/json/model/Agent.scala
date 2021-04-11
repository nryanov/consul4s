package consul4s.zio.json.model

import consul4s.model.CheckStatus
import consul4s.model.agent._
import consul4s.model.health.HealthCheck
import zio.json.{DeriveJsonCodec, JsonCodec}
import consul4s.zio.json.macros.ConverterMacros

trait Agent { this: Common with Health =>
  private[zio] case class ScriptCheckRepr(
    Name: String,
    Args: List[String],
    Timeout: Option[String] = None,
    Interval: Option[String] = None,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val scriptCheckCodec: JsonCodec[ScriptCheck] = ConverterMacros.derive[ScriptCheckRepr, ScriptCheck]

  private[zio] case class HttpCheckRepr(
    Name: String,
    HTTP: String,
    TLSSkipVerify: Boolean,
    Interval: String,
    Timeout: String,
    Header: Option[Map[String, List[String]]] = None,
    Method: Option[String] = None,
    Body: Option[String] = None,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val httpCheckCodec: JsonCodec[HttpCheck] = ConverterMacros.derive[HttpCheckRepr, HttpCheck]

  private[zio] case class TCPCheckRepr(
    Name: String,
    TCP: String,
    Interval: String,
    Timeout: String,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val tcpCheckCodec: JsonCodec[TCPCheck] = ConverterMacros.derive[TCPCheckRepr, TCPCheck]

  private[zio] case class TTLCheckRepr(
    Name: String,
    TTL: String,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val ttlCheckCodec: JsonCodec[TTLCheck] = ConverterMacros.derive[TTLCheckRepr, TTLCheck]

  private[zio] case class DockerCheckRepr(
    Name: String,
    DockerContainerId: String,
    Shell: String,
    Args: List[String],
    Interval: Option[String] = None,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val dockerCheckCodec: JsonCodec[DockerCheck] = ConverterMacros.derive[DockerCheckRepr, DockerCheck]

  private[zio] case class GRpcCheckRepr(
    Name: String,
    GRPC: String,
    GRPCUseTLS: Boolean,
    Interval: Option[String] = None,
    ID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val grpcCheckCodec: JsonCodec[GRpcCheck] = ConverterMacros.derive[GRpcCheckRepr, GRpcCheck]

  private[zio] case class AliasCheckRepr(ID: String, AliasNode: Option[String] = None, AliasService: Option[String] = None)

  implicit val aliasCheckCodec: JsonCodec[AliasCheck] = ConverterMacros.derive[AliasCheckRepr, AliasCheck]

  implicit val checkCodec: JsonCodec[Check] = DeriveJsonCodec.gen[Check]

  private[zio] case class ServiceScriptCheckRepr(
    Name: String,
    Args: List[String],
    Timeout: Option[String] = None,
    Interval: Option[String] = None,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceScriptCheckCodec: JsonCodec[ServiceScriptCheck] = ConverterMacros.derive[ServiceScriptCheckRepr, ServiceScriptCheck]

  private[zio] case class ServiceHttpCheckRepr(
    Name: String,
    HTTP: String,
    TLSSkipVerify: Boolean,
    Interval: String,
    Timeout: String,
    Header: Option[Map[String, List[String]]] = None,
    Method: Option[String] = None,
    Body: Option[String] = None,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceHttpCheckCodec: JsonCodec[ServiceHttpCheck] = ConverterMacros.derive[ServiceHttpCheckRepr, ServiceHttpCheck]

  private[zio] case class ServiceTCPCheckRepr(
    Name: String,
    TCP: String,
    Interval: String,
    Timeout: String,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceTCPCheckCodec: JsonCodec[ServiceTCPCheck] = ConverterMacros.derive[ServiceTCPCheckRepr, ServiceTCPCheck]

  private[zio] case class ServiceTTLCheckRepr(
    Name: String,
    TTL: String,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceTTLCheckCodec: JsonCodec[ServiceTTLCheck] = ConverterMacros.derive[ServiceTTLCheckRepr, ServiceTTLCheck]

  private[zio] case class ServiceDockerCheckRepr(
    Name: String,
    DockerContainerId: String,
    Shell: String,
    Args: List[String],
    Interval: Option[String] = None,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceDockerCheckCodec: JsonCodec[ServiceDockerCheck] = ConverterMacros.derive[ServiceDockerCheckRepr, ServiceDockerCheck]

  private[zio] case class ServiceGRpcCheckRepr(
    Name: String,
    GRPC: String,
    GRPCUseTLS: Boolean,
    Interval: Option[String] = None,
    CheckID: Option[String] = None,
    ServiceID: Option[String] = None,
    Status: CheckStatus = CheckStatus.Critical,
    Notes: Option[String] = None,
    SuccessBeforePassing: Int = 0,
    FailuresBeforeCritical: Int = 0,
    DeregisterCriticalServiceAfter: Option[String] = None
  )

  implicit val serviceGRpcCheckCodec: JsonCodec[ServiceGRpcCheck] = ConverterMacros.derive[ServiceGRpcCheckRepr, ServiceGRpcCheck]

  private[zio] case class ServiceAliasCheckRepr(CheckID: String, AliasNode: Option[String] = None, AliasService: Option[String] = None)

  implicit val serviceAliasCheckCodec: JsonCodec[ServiceAliasCheck] = ConverterMacros.derive[ServiceAliasCheckRepr, ServiceAliasCheck]

  implicit val serviceCheckCodec: JsonCodec[ServiceCheck] = DeriveJsonCodec.gen[ServiceCheck]

  private[zio] case class MemberInfoRepr(
    Name: String,
    Addr: String,
    Port: Int,
    Tags: Option[Map[String, String]],
    Status: Int,
    ProtocolMin: Int,
    ProtocolMax: Int,
    ProtocolCur: Int,
    DelegateMin: Int,
    DelegateMax: Int,
    DelegateCur: Int
  )

  implicit val memberInfoCodec: JsonCodec[MemberInfo] = ConverterMacros.derive[MemberInfoRepr, MemberInfo]

  private[zio] case class CheckUpdateRepr(Status: CheckStatus, Output: Option[String])

  implicit val checkUpdateCodec: JsonCodec[CheckUpdate] = ConverterMacros.derive[CheckUpdateRepr, CheckUpdate]

  private[zio] case class ServiceRepr(
    Service: String,
    ID: String,
    Tags: Option[List[String]],
    Address: String,
    TaggedAddresses: Option[Map[String, TaggedAddress]],
    Meta: Option[Map[String, String]],
    Port: Int,
    EnableTagOverride: Boolean,
    Weights: Weights
  )

  implicit val serviceCodec: JsonCodec[Service] = ConverterMacros.derive[ServiceRepr, Service]

  private[zio] case class NewServiceRepr(
    Name: String,
    ID: Option[String] = None,
    Tags: Option[List[String]] = None,
    Address: Option[String] = None,
    TaggedAddresses: Option[Map[String, TaggedAddress]] = None,
    Meta: Option[Map[String, String]] = None,
    Port: Option[Int] = None,
    Check: Option[ServiceCheck] = None,
    Checks: Option[List[ServiceCheck]] = None,
    EnableTagOverride: Boolean = false,
    Weights: Option[Weights] = None
  )

  implicit val newServiceCodec: JsonCodec[NewService] = ConverterMacros.derive[NewServiceRepr, NewService]

  private[zio] case class AggregatedServiceStatusRepr(AggregatedStatus: CheckStatus, Service: Service, Checks: List[HealthCheck])

  implicit val aggregatedServiceStatusCodec: JsonCodec[AggregatedServiceStatus] =
    ConverterMacros.derive[AggregatedServiceStatusRepr, AggregatedServiceStatus]

  private[zio] case class TokenRepr(Token: String)

  implicit val tokenCodec: JsonCodec[Token] = ConverterMacros.derive[TokenRepr, Token]

  implicit val upstreamDestTypeCodec: JsonCodec[UpstreamDestType] = JsonCodec.string.xmap(v => UpstreamDestType.withValue(v), _.value)
}
