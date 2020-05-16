package consul4s.model.agent

import consul4s.model.CheckStatus

sealed trait Check

final case class ScriptCheck(
  Name: String,
  Args: List[String],
  Interval: String,
  Timeout: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None
) extends Check

final case class HttpCheck(
  Name: String,
  HTTP: String,
  TLSSkipVerify: Boolean,
  Method: String,
  Header: Map[String, String],
  Body: String,
  Interval: String,
  Timeout: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class TCPCheck(
  Name: String,
  TCP: String,
  Interval: String,
  Timeout: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class TTLCheck(
  Name: String,
  TTL: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None
) extends Check

final case class DockerCheck(
  Name: String,
  DockerContainerId: String,
  Shell: String,
  Args: List[String],
  Interval: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class GRpcCheck(
  Name: String,
  GRPC: String,
  GRPCUseTLS: Boolean,
  Interval: String,
  ID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  SuccessBeforePassing: Int = 0,
  FailuresBeforeCritical: Int = 0
) extends Check

final case class AliasCheck(ID: String, AliasService: String) extends Check
