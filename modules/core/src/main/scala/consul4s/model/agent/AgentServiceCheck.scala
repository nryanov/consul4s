package consul4s.model.agent

import consul4s.model.Status

final case class AgentServiceCheck(
  checkID: Option[String],
  name: Option[String],
  args: Option[List[String]],
  dockerContainerID: Option[String],
  shell: Option[String],
  interval: Option[String],
  timeout: Option[String],
  ttl: Option[String],
  http: Option[String],
  header: Option[Map[String, String]],
  method: Option[String],
  body: Option[String],
  tcp: Option[String],
  status: Option[Status],
  notes: Option[String],
  tlsSkipVerify: Option[Boolean],
  gRPC: Option[String],
  gRPCUseTLS: Option[String],
  aliasNode: Option[String],
  aliasService: Option[String],
  deregisterCriticalServiceAfter: Option[String]
)
