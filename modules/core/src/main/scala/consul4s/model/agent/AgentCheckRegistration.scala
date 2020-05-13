package consul4s.model.agent

import consul4s.model.Status

/**
 *
 * @param id
 * @param name
 * @param serviceId
 * @param checkID
 * @param args
 * @param dockerContainerID
 * @param shell
 * @param interval
 * @param timeout - Can be specified in the form of "10s" or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param ttl
 * @param http
 * @param header
 * @param method
 * @param body
 * @param tcp
 * @param status
 * @param notes
 * @param tlsSkipVerify
 * @param gRPC
 * @param gRPCUseTLS
 * @param aliasNode
 * @param aliasService
 * @param deregisterCriticalServiceAfter
 */
final case class AgentCheckRegistration(
  id: Option[String] = None,
  name: String,
  serviceId: Option[String] = None,
  checkID: Option[String] = None,
  args: Option[List[String]] = None,
  dockerContainerID: Option[String] = None,
  shell: Option[String] = None,
  interval: Option[String] = None,
  timeout: Option[String] = None,
  ttl: Option[String] = None,
  http: Option[String] = None,
  header: Option[Map[String, String]] = None,
  method: Option[String] = None,
  body: Option[String] = None,
  tcp: Option[String] = None,
  status: Option[Status] = None,
  notes: Option[String] = None,
  tlsSkipVerify: Option[Boolean] = None,
  gRPC: Option[String] = None,
  gRPCUseTLS: Option[Boolean] = None,
  aliasNode: Option[String] = None,
  aliasService: Option[String] = None,
  deregisterCriticalServiceAfter: Option[String] = None
)
