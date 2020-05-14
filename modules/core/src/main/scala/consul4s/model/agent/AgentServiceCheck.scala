package consul4s.model.agent

import consul4s.model.Status

final case class AgentServiceCheck(
  CheckID: Option[String],
  Name: Option[String],
  Args: Option[List[String]],
  DockerContainerID: Option[String],
  Shell: Option[String],
  Interval: Option[String],
  Timeout: Option[String],
  TTL: Option[String],
  HTTP: Option[String],
  Header: Option[Map[String, String]],
  Method: Option[String],
  Body: Option[String],
  TCP: Option[String],
  Status: Option[Status],
  Notes: Option[String],
  TLSSkipVerify: Option[Boolean],
  GRPC: Option[String],
  GRPCUseTLS: Option[String],
  AliasNode: Option[String],
  AliasService: Option[String],
  DeregisterCriticalServiceAfter: Option[String]
)
