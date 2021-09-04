package consul4s.json4s.model

import consul4s.model.agent._
import org.json4s._
import org.json4s.JsonAST.JString
import org.json4s.FieldSerializer

trait Agent {
  class UpstreamDestTypeSerializer
      extends CustomSerializer[UpstreamDestType](implicit format =>
        (
          { case JString(value) =>
            value match {
              case UpstreamDestType(result) => result
              case _                        => throw new MappingException(s"Can't convert $value to UpstreamDestType")
            }
          },
          { case op: UpstreamDestType =>
            JString(op.value)
          }
        )
      )

  val aggregatedServiceStatusFormat: FieldSerializer[AggregatedServiceStatus] = FieldSerializer[AggregatedServiceStatus]()

  val checkUpdateFormat: FieldSerializer[CheckUpdate] = FieldSerializer[CheckUpdate]()

  val memberInfoFormat: FieldSerializer[MemberInfo] = FieldSerializer[MemberInfo]()

  val newServiceFormat: FieldSerializer[NewService] = FieldSerializer[NewService]()

  val serviceFormat: FieldSerializer[Service] = FieldSerializer[Service]()

  val taggedAddressFormat: FieldSerializer[TaggedAddress] = FieldSerializer[TaggedAddress]()

  val tokenFormat: FieldSerializer[Token] = FieldSerializer[Token]()

  val weightsFormat: FieldSerializer[Weights] = FieldSerializer[Weights]()

  class CheckSerializer
      extends CustomSerializer[Check](implicit format =>
        (
          { case _ =>
            throw new UnsupportedOperationException("Not supported")
          },
          {
            case x: ScriptCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("Args", JArray(x.Args.map(v => JString(v)))) ::
                  JField("Timeout", optionToValue(x.Timeout)) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter))
                  :: Nil
              )
            case x: HttpCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("HTTP", JString(x.HTTP)) ::
                  JField("TLSSkipVerify", JBool(x.TLSSkipVerify)) ::
                  JField("Interval", JString(x.Interval)) ::
                  JField("Timeout", JString(x.Timeout)) ::
                  JField("Header", Extraction.decompose(x.Header)) ::
                  JField("Method", optionToValue(x.Method)) ::
                  JField("Body", optionToValue(x.Body)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: TCPCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("TCP", JString(x.TCP)) ::
                  JField("Interval", JString(x.Interval)) ::
                  JField("Timeout", JString(x.Timeout)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: TTLCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("TTL", JString(x.TTL)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: DockerCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("DockerContainerID", JString(x.DockerContainerID)) ::
                  JField("Shell", JString(x.Shell)) ::
                  JField("Args", JArray(x.Args.map(v => JString(v)))) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: GrpcCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("GRPC", JString(x.GRPC)) ::
                  JField("GRPCUseTLS", JBool(x.GRPCUseTLS)) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("ID", optionToValue(x.ID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: AliasCheck =>
              JObject(
                JField("ID", JString(x.ID)) ::
                  JField("AliasNode", optionToValue(x.AliasNode)) ::
                  JField("AliasService", optionToValue(x.AliasService)) ::
                  Nil
              )
          }
        )
      )

  class ServiceCheckSerializer
      extends CustomSerializer[ServiceCheck](implicit format =>
        (
          { case _ =>
            throw new UnsupportedOperationException("Not supported")
          },
          {
            case x: ServiceScriptCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("Args", JArray(x.Args.map(v => JString(v)))) ::
                  JField("Timeout", optionToValue(x.Timeout)) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceHttpCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("HTTP", JString(x.HTTP)) ::
                  JField("TLSSkipVerify", JBool(x.TLSSkipVerify)) ::
                  JField("Interval", JString(x.Interval)) ::
                  JField("Timeout", JString(x.Timeout)) ::
                  JField("Header", Extraction.decompose(x.Header)) ::
                  JField("Method", optionToValue(x.Method)) ::
                  JField("Body", optionToValue(x.Body)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceTCPCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("TCP", JString(x.TCP)) ::
                  JField("Interval", JString(x.Interval)) ::
                  JField("Timeout", JString(x.Timeout)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceTTLCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("TTL", JString(x.TTL)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceDockerCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("DockerContainerID", JString(x.DockerContainerID)) ::
                  JField("Shell", JString(x.Shell)) ::
                  JField("Args", JArray(x.Args.map(v => JString(v)))) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceGrpcCheck =>
              JObject(
                JField("Name", JString(x.Name)) ::
                  JField("GRPC", JString(x.GRPC)) ::
                  JField("GRPCUseTLS", JBool(x.GRPCUseTLS)) ::
                  JField("Interval", optionToValue(x.Interval)) ::
                  JField("CheckID", optionToValue(x.CheckID)) ::
                  JField("ServiceID", optionToValue(x.ServiceID)) ::
                  JField("Status", JString(x.Status.value)) ::
                  JField("Notes", optionToValue(x.Notes)) ::
                  JField("SuccessBeforePassing", JInt(x.SuccessBeforePassing)) ::
                  JField("FailuresBeforeCritical", JInt(x.FailuresBeforeCritical)) ::
                  JField("DeregisterCriticalServiceAfter", optionToValue(x.DeregisterCriticalServiceAfter)) ::
                  Nil
              )
            case x: ServiceAliasCheck =>
              JObject(
                JField("CheckID", JString(x.CheckID)) ::
                  JField("AliasNode", optionToValue(x.AliasNode)) ::
                  JField("AliasService", optionToValue(x.AliasService)) ::
                  Nil
              )
          }
        )
      )

  val agentAllSerializers = List(new UpstreamDestTypeSerializer, new CheckSerializer, new ServiceCheckSerializer)
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
