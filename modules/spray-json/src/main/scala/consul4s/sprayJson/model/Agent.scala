package consul4s.sprayJson.model

import consul4s.model.agent._
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Agent extends DefaultJsonProtocol { this: Health with Catalog with Common =>
  implicit val agentAuthorizeFormat: RootJsonFormat[AgentAuthorize] = jsonFormat(AgentAuthorize.apply, "Authorized", "Reason")

  implicit val agentAuthorizeParamsFormat: RootJsonFormat[AgentAuthorizeParams] =
    jsonFormat(AgentAuthorizeParams.apply, "Target", "ClientCertUri", "ClientCertSerial")

  implicit val agentCheckFormat: RootJsonFormat[AgentCheck] = jsonFormat(
    AgentCheck.apply,
    "Node",
    "CheckID",
    "Name",
    "Status",
    "Notes",
    "Output",
    "ServiceID",
    "ServiceName",
    "Type",
    "Definition",
    "Namespace"
  )

  implicit val agentCheckRegistrationFormat: RootJsonFormat[AgentCheckRegistration] =
    jsonFormat(AgentCheckRegistration.apply, "ID", "Name", "Notes", "ServiceId", "AgentServiceCheck", "Namespace")

  implicit val agentMemberFormat: RootJsonFormat[AgentMember] = jsonFormat(
    AgentMember.apply,
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

  implicit val agentServiceFormat: RootJsonFormat[AgentService] = jsonFormat(
    AgentService.apply,
    "Kind",
    "ID",
    "Service",
    "Tags",
    "Meta",
    "Port",
    "Address",
    "TaggedAddresses",
    "Weights",
    "EnableTagOverride",
    "CreateIndex",
    "ModifyIndex",
    "ContentHash",
    "Proxy",
    "Connect"
  )

  implicit val agentServiceCheckFormat: RootJsonFormat[AgentServiceCheck] = jsonFormat(
    AgentServiceCheck.apply,
    "CheckID",
    "Name",
    "ScriptArgs",
    "DockerContainerID",
    "Shell",
    "Interval",
    "Timeout",
    "TTL",
    "HTTP",
    "Header",
    "Method",
    "Body",
    "TCP",
    "Status",
    "Notes",
    "TLSSkipVerify",
    "GRPC",
    "GRPCUseTLS",
    "AliasNode",
    "AliasService",
    "DeregisterCriticalServiceAfter"
  )

  implicit val agentServiceChecksInfoFormat: RootJsonFormat[AgentServiceChecksInfo] = jsonFormat(
    AgentServiceChecksInfo.apply,
    "AggregatedStatus",
    "Service",
    "Checks"
  )

  implicit val agentServiceConnectFormat: RootJsonFormat[AgentServiceConnect] =
    jsonFormat(AgentServiceConnect.apply, "Native", "SedicarService")

  implicit val agentServiceConnectProxyConfigFormat: RootJsonFormat[AgentServiceConnectProxyConfig] = jsonFormat(
    AgentServiceConnectProxyConfig.apply,
    "DestinationServiceName",
    "DestinationServiceID",
    "LocalServiceAddress",
    "LocalServicePort",
    "Config",
    "Upstreams"
  )

  implicit val agentServiceRegistrationFormat: RootJsonFormat[AgentServiceRegistration] = jsonFormat(
    AgentServiceRegistration.apply,
    "Kind",
    "ID",
    "Name",
    "Tags",
    "Port",
    "Address",
    "TaggedAddresses",
    "EnableTagOverride",
    "Meta",
    "Weights",
    "Check",
    "Checks",
    "Proxy",
    "Connect",
    "Namespace"
  )

  implicit val agentTokenFormat: RootJsonFormat[AgentToken] = jsonFormat(AgentToken, "Token")

  implicit val agentWeightsFormat: RootJsonFormat[AgentWeights] = jsonFormat(AgentWeights.apply, "Passing", "Warning")

  implicit val connectProxyConfigFormat: RootJsonFormat[ConnectProxyConfig] =
    jsonFormat(ConnectProxyConfig.apply, "ProxyServiceId", "TargetServiceId", "TargetServiceName", "ContentHash", "Config", "Upstreams")

  implicit val membersOptsFormat: RootJsonFormat[MembersOpts] = jsonFormat(MembersOpts.apply, "WAN", "Segment")

  implicit val serviceRegisterOptsFormat: RootJsonFormat[ServiceRegisterOpts] = jsonFormat(ServiceRegisterOpts, "ReplaceExistingChecks")

  implicit val upstreamFormat: RootJsonFormat[Upstream] = jsonFormat(
    Upstream.apply,
    "DestinationType",
    "DestinationNamespace",
    "DestinationName",
    "Datacenter",
    "LocalBindAddress",
    "LocalBindPort",
    "Config"
  )

  implicit object UpstreamDestTypeFormat extends RootJsonFormat[UpstreamDestType] {
    override def write(obj: UpstreamDestType): JsValue = JsString(obj.value)

    override def read(json: JsValue): UpstreamDestType = json match {
      case JsString(value) => UpstreamDestType.withValue(value)
      case _               => deserializationError("UpstreamDestType expected")
    }
  }
}
