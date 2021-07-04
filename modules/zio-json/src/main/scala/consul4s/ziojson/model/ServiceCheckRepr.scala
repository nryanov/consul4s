package consul4s.ziojson.model

import consul4s.model.CheckStatus
import consul4s.model.agent._
import zio.json.jsonDiscriminator

// Custom model representation because of object discriminator @jsonDiscriminator("")
@jsonDiscriminator("") sealed trait ServiceCheckRepr

object ServiceCheckRepr {
  def toServiceCheck(inst: ServiceCheckRepr): ServiceCheck = inst match {
    case v: ServiceScriptCheckRepr =>
      ServiceScriptCheck(
        v.Name,
        v.Args,
        v.Timeout,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceHttpCheckRepr =>
      ServiceHttpCheck(
        v.Name,
        v.HTTP,
        v.TLSSkipVerify,
        v.Interval,
        v.Timeout,
        v.Header,
        v.Method,
        v.Body,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceTCPCheckRepr =>
      ServiceTCPCheck(
        v.Name,
        v.TCP,
        v.Interval,
        v.Timeout,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceTTLCheckRepr =>
      ServiceTTLCheck(
        v.Name,
        v.TTL,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceDockerCheckRepr =>
      ServiceDockerCheck(
        v.Name,
        v.DockerContainerID,
        v.Shell,
        v.Args,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceGRpcCheckRepr =>
      ServiceGRpcCheck(
        v.Name,
        v.GRPC,
        v.GRPCUseTLS,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceAliasCheckRepr =>
      ServiceAliasCheck(
        v.CheckID,
        v.AliasNode,
        v.AliasService
      )
  }

  def fromServiceCheck(inst: ServiceCheck): ServiceCheckRepr = inst match {
    case v: ServiceScriptCheck =>
      ServiceScriptCheckRepr(
        v.Name,
        v.Args,
        v.Timeout,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceHttpCheck =>
      ServiceHttpCheckRepr(
        v.Name,
        v.HTTP,
        v.TLSSkipVerify,
        v.Interval,
        v.Timeout,
        v.Header,
        v.Method,
        v.Body,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceTCPCheck =>
      ServiceTCPCheckRepr(
        v.Name,
        v.TCP,
        v.Interval,
        v.Timeout,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceTTLCheck =>
      ServiceTTLCheckRepr(
        v.Name,
        v.TTL,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceDockerCheck =>
      ServiceDockerCheckRepr(
        v.Name,
        v.DockerContainerID,
        v.Shell,
        v.Args,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceGRpcCheck =>
      ServiceGRpcCheckRepr(
        v.Name,
        v.GRPC,
        v.GRPCUseTLS,
        v.Interval,
        v.CheckID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: ServiceAliasCheck =>
      ServiceAliasCheckRepr(
        v.CheckID,
        v.AliasNode,
        v.AliasService
      )
  }

}

final case class ServiceScriptCheckRepr(
  Name: String,
  Args: List[String],
  Timeout: Option[String] = None,
  Interval: Option[String] = None,
  CheckID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends ServiceCheckRepr

final case class ServiceHttpCheckRepr(
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
) extends ServiceCheckRepr

final case class ServiceTCPCheckRepr(
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
) extends ServiceCheckRepr

final case class ServiceTTLCheckRepr(
  Name: String,
  TTL: String,
  CheckID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends ServiceCheckRepr

final case class ServiceDockerCheckRepr(
  Name: String,
  DockerContainerID: String,
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
) extends ServiceCheckRepr

final case class ServiceGRpcCheckRepr(
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
) extends ServiceCheckRepr

final case class ServiceAliasCheckRepr(
  CheckID: String,
  AliasNode: Option[String] = None,
  AliasService: Option[String] = None
) extends ServiceCheckRepr
