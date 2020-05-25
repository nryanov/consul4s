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
      .orElse(renameFrom("Addr", "address"))
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

  class CheckSerializer
      extends CustomSerializer[Check](
        implicit format =>
          (
            {
              case _ => throw new UnsupportedOperationException("Not supported")
            }, {
              case x: ScriptCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("Args", JArray(x.args.map(v => JString(v)))) ::
                    JField("Timeout", optionToValue(x.timeout)) ::
                    JField("Interval", optionToValue(x.interval)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: HttpCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("HTTP", JString(x.http)) ::
                    JField("TLSSkipVerify", JBool(x.tlsSkipVerify)) ::
                    JField("Interval", JString(x.interval)) ::
                    JField("Timeout", JString(x.timeout)) ::
                    JField("Header", Extraction.decompose(x.header)) ::
                    JField("Method", optionToValue(x.method)) ::
                    JField("Body", optionToValue(x.body)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("SuccessBeforePassing", JInt(x.successBeforePassing)) ::
                    JField("FailuresBeforeCritical", JInt(x.failuresBeforeCritical)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: TCPCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("TCP", JString(x.tcp)) ::
                    JField("Interval", JString(x.interval)) ::
                    JField("Timeout", JString(x.timeout)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("SuccessBeforePassing", JInt(x.successBeforePassing)) ::
                    JField("FailuresBeforeCritical", JInt(x.failuresBeforeCritical)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: TTLCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("TTL", JString(x.ttl)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: DockerCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("DockerContainerId", JString(x.dockerContainerId)) ::
                    JField("Shell", JString(x.shell)) ::
                    JField("Args", JArray(x.args.map(v => JString(v)))) ::
                    JField("Interval", optionToValue(x.interval)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("SuccessBeforePassing", JInt(x.successBeforePassing)) ::
                    JField("FailuresBeforeCritical", JInt(x.failuresBeforeCritical)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: GRpcCheck =>
                JObject(
                  JField("Name", JString(x.name)) ::
                    JField("GRPC", JString(x.grpc)) ::
                    JField("GRPCUseTLS", JBool(x.grpcUseTLS)) ::
                    JField("Interval", optionToValue(x.interval)) ::
                    JField("ID", optionToValue(x.id)) ::
                    JField("ServiceID", optionToValue(x.serviceId)) ::
                    JField("Status", JString(x.status.value)) ::
                    JField("Notes", optionToValue(x.notes)) ::
                    JField("SuccessBeforePassing", JInt(x.successBeforePassing)) ::
                    JField("FailuresBeforeCritical", JInt(x.failuresBeforeCritical)) ::
                    JField("DeregisterCriticalServiceAfter", optionToValue(x.deregisterCriticalServiceAfter))
                    :: Nil
                )
              case x: AliasCheck =>
                JObject(
                  JField("ID", JString(x.id)) ::
                    JField("AliasNode", optionToValue(x.aliasNode)) ::
                    JField("AliasService", optionToValue(x.aliasService)) :: Nil
                )
            }
          )
      )

  val agentAllSerializers = List(new UpstreamDestTypeSerializer, new CheckSerializer)
  val agentAllFieldSerializers = List(
    aggregatedServiceStatusFormat,
    checkUpdateFormat,
    memberInfoFormat,
    newServiceFormat,
    serviceFormat,
    taggedAddressFormat,
    tokenFormat,
    weightsFormat
  )
}
