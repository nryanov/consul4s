package consul4s.ziojson.model

import consul4s.model.CheckStatus
import consul4s.model.agent._
import zio.json.jsonDiscriminator

// Custom model representation because of object discriminator @jsonDiscriminator("")
@jsonDiscriminator("") sealed trait CheckRepr

object CheckRepr {
  def toCheck(inst: CheckRepr): Check = inst match {
    case v: ScriptCheckRepr =>
      ScriptCheck(
        v.Name,
        v.Args,
        v.Timeout,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: HttpCheckRepr =>
      HttpCheck(
        v.Name,
        v.HTTP,
        v.TLSSkipVerify,
        v.Interval,
        v.Timeout,
        v.Header,
        v.Method,
        v.Body,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: TCPCheckRepr =>
      TCPCheck(
        v.Name,
        v.TCP,
        v.Interval,
        v.Timeout,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: TTLCheckRepr =>
      TTLCheck(
        v.Name,
        v.TTL,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: DockerCheckRepr =>
      DockerCheck(
        v.Name,
        v.DockerContainerID,
        v.Shell,
        v.Args,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: GRpcCheckRepr =>
      GRpcCheck(
        v.Name,
        v.GRPC,
        v.GRPCUseTLS,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: AliasCheckRepr =>
      AliasCheck(
        v.ID,
        v.AliasNode,
        v.AliasService
      )
  }

  def fromCheck(inst: Check): CheckRepr = inst match {
    case v: ScriptCheck =>
      ScriptCheckRepr(
        v.Name,
        v.Args,
        v.Timeout,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: HttpCheck =>
      HttpCheckRepr(
        v.Name,
        v.HTTP,
        v.TLSSkipVerify,
        v.Interval,
        v.Timeout,
        v.Header,
        v.Method,
        v.Body,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: TCPCheck =>
      TCPCheckRepr(
        v.Name,
        v.TCP,
        v.Interval,
        v.Timeout,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: TTLCheck =>
      TTLCheckRepr(
        v.Name,
        v.TTL,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.DeregisterCriticalServiceAfter
      )
    case v: DockerCheck =>
      DockerCheckRepr(
        v.Name,
        v.DockerContainerID,
        v.Shell,
        v.Args,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: GRpcCheck =>
      GRpcCheckRepr(
        v.Name,
        v.GRPC,
        v.GRPCUseTLS,
        v.Interval,
        v.ID,
        v.ServiceID,
        v.Status,
        v.Notes,
        v.SuccessBeforePassing,
        v.FailuresBeforeCritical,
        v.DeregisterCriticalServiceAfter
      )
    case v: AliasCheck =>
      AliasCheckRepr(
        v.ID,
        v.AliasNode,
        v.AliasService
      )
  }
}

final case class ScriptCheckRepr(
  Name: String,
  Args: List[String],
  Timeout: Option[String] = None,
  Interval: Option[String] = None,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends CheckRepr

final case class HttpCheckRepr(
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
) extends CheckRepr

final case class TCPCheckRepr(
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
) extends CheckRepr

final case class TTLCheckRepr(
  Name: String,
  TTL: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends CheckRepr

final case class DockerCheckRepr(
  Name: String,
  DockerContainerID: String,
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
) extends CheckRepr

final case class GRpcCheckRepr(
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
) extends CheckRepr

final case class AliasCheckRepr(ID: String, AliasNode: Option[String] = None, AliasService: Option[String] = None) extends CheckRepr
