package consul4s.model.agent

import consul4s.model.CheckStatus

sealed trait Check

final case class ScriptCheck(
  Name: String,
  ID: String,
  Args: List[String],
  Interval: String,
  Timeout: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None
) extends Check

final case class HttpCheck(
  Name: String,
  ID: String,
  HTTP: String,
  TLSSkipVerify: Boolean,
  Method: String,
  Header: Map[String, String],
  Body: String,
  Interval: String,
  Timeout: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class TCPCheck(
  Name: String,
  ID: String,
  TCP: String,
  Interval: String,
  Timeout: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class TTLCheck(
  Name: String,
  ID: String,
  TTL: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None
) extends Check

final case class DockerCheck(
  Name: String,
  ID: String,
  DockerContainerId: String,
  Shell: String,
  Args: List[String],
  Interval: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class GRpcCheck(
  Name: String,
  ID: String,
  GRPC: String,
  GRPCUseTLS: Boolean,
  Interval: String,
  ServiceID: Option[String] = None,
  Status: CheckStatus,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class AliasCheck(ID: String, AliasService: String) extends Check
