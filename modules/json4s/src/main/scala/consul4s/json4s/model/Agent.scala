package consul4s.json4s.model

import consul4s.model.agent._
import org.json4s._
import org.json4s.JsonAST.JString
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Agent {
  class UpstreamDestTypeSerializer
      extends CustomSerializer[UpstreamDestType](
        implicit format =>
          (
            {
              case JString(value) => UpstreamDestType.withValue(value)
            }, {
              case op: UpstreamDestType => JString(op.value)
            }
          )
      )

  val aggregatedServiceStatusFormat = FieldSerializer[AggregatedServiceStatus](
    Map(),
    renameFrom("AggregatedStatus", "aggregatedStatus").orElse(renameFrom("Service", "service")).orElse(renameFrom("Checks", "checks"))
  )

  val checkUpdateFormat = FieldSerializer[CheckUpdate](
    renameTo("status", "Status").orElse(renameTo("output", "Output")),
    Map()
  )

  val memberInfoFormat = FieldSerializer[MemberInfo](
    Map(),
    renameFrom("Name", "name")
      .orElse(renameFrom("Addr", "addr"))
      .orElse(renameFrom("Port", "port"))
      .orElse(renameFrom("Tags", "tags"))
      .orElse(renameFrom("Status", "status"))
      .orElse(renameFrom("ProtocolMin", "protocolMin"))
      .orElse(renameFrom("ProtocolMax", "protocolMax"))
      .orElse(renameFrom("ProtocolCur", "protocolCur"))
      .orElse(renameFrom("DelegateMin", "delegateMin"))
      .orElse(renameFrom("DelegateMax", "delegateMax"))
      .orElse(renameFrom("DelegateCur", "delegateCur"))
  )

  val newServiceFormat = FieldSerializer[NewService](
    renameTo("name", "Name")
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("tags", "Tags"))
      .orElse(renameTo("address", "Address"))
      .orElse(renameTo("taggedAddresses", "TaggedAddresses"))
      .orElse(renameTo("meta", "Meta"))
      .orElse(renameTo("port", "Port"))
      .orElse(renameTo("check", "Check"))
      .orElse(renameTo("checks", "Checks"))
      .orElse(renameTo("enableTagOverride", "EnableTagOverride"))
      .orElse(renameTo("weights", "Weights")),
    Map()
  )

  val serviceFormat = FieldSerializer[Service](
    Map(),
    renameFrom("Service", "service")
      .orElse(renameFrom("ID", "id"))
      .orElse(renameFrom("Tags", "tags"))
      .orElse(renameFrom("Address", "address"))
      .orElse(renameFrom("TaggedAddresses", "taggedAddresses"))
      .orElse(renameFrom("Meta", "meta"))
      .orElse(renameFrom("Port", "port"))
      .orElse(renameFrom("EnableTagOverride", "enableTagOverride"))
      .orElse(renameFrom("Weights", "weights"))
  )

  val taggedAddressFormat = FieldSerializer[TaggedAddress](
    renameTo("address", "Address").orElse(renameTo("port", "Port")),
    renameFrom("Address", "address").orElse(renameFrom("Port", "port"))
  )

  val tokenFormat = FieldSerializer[Token](renameTo("token", "Token"), Map())

  val weightsFormat = FieldSerializer[Weights](
    renameTo("passing", "Passing").orElse(renameTo("warning", "Warning")),
    renameFrom("Passing", "passing").orElse(renameFrom("Warning", "warning"))
  )

  val ScriptCheckFormat = FieldSerializer[ScriptCheck](
    renameTo("name", "Name")
      .orElse(renameTo("args", "Args"))
      .orElse(renameTo("timeout", "Timeout"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val HttpCheckFormat = FieldSerializer[HttpCheck](
    renameTo("name", "Name")
      .orElse(renameTo("http", "HTTP"))
      .orElse(renameTo("tlsSkipVerify", "TLSSkipVerify"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("timeout", "Timeout"))
      .orElse(renameTo("header", "Header"))
      .orElse(renameTo("method", "Method"))
      .orElse(renameTo("body", "Body"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("successBeforePassing", "SuccessBeforePassing"))
      .orElse(renameTo("failuresBeforeCritical", "FailuresBeforeCritical"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val TCPCheckFormat = FieldSerializer[TCPCheck](
    renameTo("name", "Name")
      .orElse(renameTo("tcp", "TCP"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("timeout", "Timeout"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("successBeforePassing", "SuccessBeforePassing"))
      .orElse(renameTo("failuresBeforeCritical", "FailuresBeforeCritical"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val TTLCheckFormat = FieldSerializer[TTLCheck](
    renameTo("name", "Name")
      .orElse(renameTo("ttl", "TTL"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val DockerCheckFormat = FieldSerializer[DockerCheck](
    renameTo("name", "Name")
      .orElse(renameTo("dockerContainerId", "DockerContainerId"))
      .orElse(renameTo("shell", "Shell"))
      .orElse(renameTo("args", "Args"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("successBeforePassing", "SuccessBeforePassing"))
      .orElse(renameTo("failuresBeforeCritical", "FailuresBeforeCritical"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val GRpcCheckFormat = FieldSerializer[GRpcCheck](
    renameTo("name", "Name")
      .orElse(renameTo("grpc", "GRPC"))
      .orElse(renameTo("grpcUseTLS", "GRPCUseTLS"))
      .orElse(renameTo("interval", "Interval"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("serviceId", "ServiceID"))
      .orElse(renameTo("status", "Status"))
      .orElse(renameTo("notes", "Notes"))
      .orElse(renameTo("successBeforePassing", "SuccessBeforePassing"))
      .orElse(renameTo("failuresBeforeCritical", "FailuresBeforeCritical"))
      .orElse(renameTo("deregisterCriticalServiceAfter", "DeregisterCriticalServiceAfter")),
    Map()
  )

  val AliasCheckFormat = FieldSerializer[AliasCheck](
    renameTo("id", "ID").orElse(renameTo("aliasNode", "AliasNode")).orElse(renameTo("aliasService", "AliasService")),
    Map()
  )

  val agentAllSerializers = List(new UpstreamDestTypeSerializer)
  val agentAllFieldSerializers = List(
    aggregatedServiceStatusFormat,
    checkUpdateFormat,
    memberInfoFormat,
    newServiceFormat,
    serviceFormat,
    taggedAddressFormat,
    tokenFormat,
    weightsFormat,
    ScriptCheckFormat,
    HttpCheckFormat,
    TCPCheckFormat,
    TTLCheckFormat,
    DockerCheckFormat,
    GRpcCheckFormat,
    AliasCheckFormat
  )
}
