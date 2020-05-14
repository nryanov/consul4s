package consul4s.model.agent

import consul4s.model.Status

final case class AgentCheckRegistration(
  ID: Option[String] = None,
  Name: String,
  ServiceId: Option[String] = None,
  CheckID: Option[String] = None,
  Args: Option[List[String]] = None,
  DockerContainerID: Option[String] = None,
  Shell: Option[String] = None,
  Interval: Option[String] = None,
  Timeout: Option[String] = None,
  TTL: Option[String] = None,
  HTTP: Option[String] = None,
  Header: Option[Map[String, String]] = None,
  Method: Option[String] = None,
  Body: Option[String] = None,
  TCP: Option[String] = None,
  Status: Option[Status] = None,
  Notes: Option[String] = None,
  TLSSkipVerify: Option[Boolean] = None,
  GRPC: Option[String] = None,
  GRPCUseTLS: Option[Boolean] = None,
  AliasNode: Option[String] = None,
  AliasService: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
)
