package consul4s.json4s.model

import consul4s.model.{ServiceKind, Status}
import consul4s.model.agent._
import consul4s.model.catalog.ServiceAddress
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, JObject}

trait Agent {
  val agentAuthorizeSerializer = new CustomSerializer[AgentAuthorize](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentAuthorize(
              (json \ "Authorized").extract[Boolean],
              (json \ "Reason").extract[String]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentAuthorizeParamsSerializer = new CustomSerializer[AgentAuthorizeParams](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentAuthorizeParams(
              (json \ "Target").extract[String],
              (json \ "ClientCertUri").extract[String],
              (json \ "ClientCertSerial").extract[String]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentCheckSerializer = new CustomSerializer[AgentCheck](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentCheck(
              (json \ "Node").extract[String],
              (json \ "CheckID").extract[String],
              (json \ "Name").extract[String],
              (json \ "Status").extract[Status],
              (json \ "Notes").extract[String],
              (json \ "Output").extract[String],
              (json \ "ServiceID").extract[String],
              (json \ "ServiceName").extract[String],
              (json \ "Type").extract[String],
              (json \ "Definition").extract[HealthCheckDefinition],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentCheckRegistrationSerializer = new CustomSerializer[AgentCheckRegistration](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentCheckRegistration(
              (json \ "ID").extract[Option[String]],
              (json \ "Name").extract[Option[String]],
              (json \ "Notes").extract[Option[String]],
              (json \ "ServiceId").extract[Option[String]],
              (json \ "AgentServiceCheck").extract[Option[AgentServiceCheck]],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentMemberSerializer = new CustomSerializer[AgentMember](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentMember(
              (json \ "Name").extract[String],
              (json \ "Addr").extract[String],
              (json \ "Port").extract[Int],
              (json \ "Tags").extract[Map[String, String]],
              (json \ "Status").extract[Int],
              (json \ "ProtocolMin").extract[Int],
              (json \ "ProtocolMax").extract[Int],
              (json \ "ProtocolCur").extract[Int],
              (json \ "DelegateMin").extract[Int],
              (json \ "DelegateMax").extract[Int],
              (json \ "DelegateCur").extract[Int]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceSerializer = new CustomSerializer[AgentService](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentService(
              (json \ "Kind").extract[Option[ServiceKind]],
              (json \ "ID").extract[String],
              (json \ "Service").extract[String],
              (json \ "Tags").extract[List[String]],
              (json \ "Meta").extract[Map[String, String]],
              (json \ "Port").extract[Int],
              (json \ "Address").extract[String],
              (json \ "TaggedAddresses").extract[Option[Map[String, ServiceAddress]]],
              (json \ "Weights").extract[AgentWeights],
              (json \ "EnableTagOverride").extract[Boolean],
              (json \ "CreateIndex").extract[Option[Long]],
              (json \ "ModifyIndex").extract[Option[Long]],
              (json \ "ContentHash").extract[Option[String]],
              (json \ "Proxy").extract[Option[AgentServiceConnectProxyConfig]],
              (json \ "Connect").extract[Option[AgentServiceConnect]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceCheckSerializer = new CustomSerializer[AgentServiceCheck](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentServiceCheck(
              (json \ "CheckID").extract[Option[String]],
              (json \ "Name").extract[Option[String]],
              (json \ "ScriptArgs").extract[Option[List[String]]],
              (json \ "DockerContainerID").extract[Option[String]],
              (json \ "Shell").extract[Option[String]],
              (json \ "Interval").extract[Option[String]],
              (json \ "Timeout").extract[Option[String]],
              (json \ "TTL").extract[Option[String]],
              (json \ "HTTP").extract[Option[String]],
              (json \ "Header").extract[Option[Map[String, String]]],
              (json \ "Method").extract[Option[String]],
              (json \ "Body").extract[Option[String]],
              (json \ "TCP").extract[Option[String]],
              (json \ "Status").extract[Option[Status]],
              (json \ "Notes").extract[Option[String]],
              (json \ "TLSSkipVerify").extract[Option[Boolean]],
              (json \ "GRPC").extract[Option[String]],
              (json \ "GRPCUseTLS").extract[Option[String]],
              (json \ "AliasNode").extract[Option[String]],
              (json \ "AliasService").extract[Option[String]],
              (json \ "DeregisterCriticalServiceAfter").extract[Option[String]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceChecksInfoSerializer = new CustomSerializer[AgentServiceChecksInfo](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentServiceChecksInfo(
              (json \ "AggregatedStatus").extract[String],
              (json \ "Service").extract[AgentService],
              (json \ "Checks").extract[List[HealthCheck]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceConnectSerializer = new CustomSerializer[AgentServiceConnect](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentServiceConnect(
              (json \ "Native").extract[Option[Boolean]],
              (json \ "SedicarService").extract[Option[AgentServiceRegistration]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceConnectProxyConfigSerializer = new CustomSerializer[AgentServiceConnectProxyConfig](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentServiceConnectProxyConfig(
              (json \ "DestinationServiceName").extract[Option[String]],
              (json \ "DestinationServiceID").extract[Option[String]],
              (json \ "LocalServiceAddress").extract[Option[String]],
              (json \ "LocalServicePort").extract[Option[Int]],
              (json \ "Config").extract[Option[Map[String, String]]],
              (json \ "Upstreams").extract[Option[Upstream]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentServiceRegistrationSerializer = new CustomSerializer[AgentServiceRegistration](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentServiceRegistration(
              (json \ "kind").extract[Option[ServiceKind]],
              (json \ "id").extract[Option[String]],
              (json \ "name").extract[Option[String]],
              (json \ "tags").extract[Option[List[String]]],
              (json \ "port").extract[Option[Int]],
              (json \ "address").extract[Option[String]],
              (json \ "taggedAddresses").extract[Option[Map[String, ServiceAddress]]],
              (json \ "enableTagOverride").extract[Option[Boolean]],
              (json \ "meta").extract[Option[Map[String, String]]],
              (json \ "weights").extract[AgentWeights],
              (json \ "check").extract[AgentServiceCheck],
              (json \ "checks").extract[List[AgentServiceCheck]],
              (json \ "proxy").extract[Option[AgentServiceConnectProxyConfig]],
              (json \ "connect").extract[Option[AgentServiceConnect]],
              (json \ "namespace").extract[Option[String]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentTokenSerializer = new CustomSerializer[AgentToken](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentToken(
              (json \ "Token").extract[String]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val agentWeightsSerializer = new CustomSerializer[AgentWeights](
    implicit format =>
      (
        {
          case json: JObject =>
            AgentWeights(
              (json \ "Passing").extract[Int],
              (json \ "Warning").extract[Int]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val connectProxyConfigSerializer = new CustomSerializer[ConnectProxyConfig](
    implicit format =>
      (
        {
          case json: JObject =>
            ConnectProxyConfig(
              (json \ "ProxyServiceId").extract[String],
              (json \ "TargetServiceId").extract[String],
              (json \ "TargetServiceName").extract[String],
              (json \ "ContentHash").extract[String],
              (json \ "Config").extract[Map[String, String]],
              (json \ "Upstreams").extract[List[Upstream]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val membersOptsSerializer = new CustomSerializer[MembersOpts](
    implicit format =>
      (
        {
          case json: JObject =>
            MembersOpts(
              (json \ "WAN").extract[Boolean],
              (json \ "Segment").extract[String]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val serviceRegisterOptsSerializer = new CustomSerializer[ServiceRegisterOpts](
    implicit format =>
      (
        {
          case json: JObject =>
            ServiceRegisterOpts(
              (json \ "ReplaceExistingChecks").extract[Boolean]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val upstreamSerializer = new CustomSerializer[Upstream](
    implicit format =>
      (
        {
          case json: JObject =>
            Upstream(
              (json \ "DestinationType").extract[Option[UpstreamDestType]],
              (json \ "DestinationNamespace").extract[Option[String]],
              (json \ "DestinationName").extract[String],
              (json \ "Datacenter").extract[Option[String]],
              (json \ "LocalBindAddress").extract[Option[String]],
              (json \ "LocalBindPort").extract[Option[Int]],
              (json \ "Config").extract[Option[Map[String, String]]]
            )
        }, {
          case _ => JObject()
        }
      )
  )

  val upstreamDestTypeSerializer = new CustomSerializer[UpstreamDestType](
    implicit format =>
      (
        {
          case JString(value) => UpstreamDestType.withValue(value)
        }, {
          case destType: UpstreamDestType => JString(destType.value)
        }
      )
  )
}
