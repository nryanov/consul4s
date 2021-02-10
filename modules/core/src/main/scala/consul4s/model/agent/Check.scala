package consul4s.model.agent

import consul4s.model.CheckStatus

/**
 * https://www.consul.io/docs/agent/checks
 */
sealed trait Check

/**
 * @param name - Specifies the name of the check.
 * @param args - Specifies command arguments to run to update the status of the check.
 * @param interval - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param timeout - Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of "10s" or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ScriptCheck(
  name: String,
  args: List[String],
  timeout: Option[String] = None,
  interval: Option[String] = None,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param name - Specifies the name of the check.
 * @param http - Specifies an HTTP check to perform a GET request against the value of HTTP (expected to be a URL) every Interval. If the response is any 2xx code, the check is passing. If the response is 429 Too Many Requests, the check is warning. Otherwise, the check is critical. HTTP checks also support SSL. By default, a valid SSL certificate is expected. Certificate verification can be controlled using the TLSSkipVerify.
 * @param tlsSkipVerify - Specifies if the certificate for an HTTPS check should not be verified.
 * @param method - Specifies a different HTTP method to be used for an HTTP check. When no value is specified, GET is used.
 * @param header - Specifies a set of headers that should be set for HTTP checks. Each header can have multiple values.
 * @param body - Specifies a body that should be sent with HTTP checks.
 * @param interval - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param timeout - - Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of "10s" or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param successBeforePassing
 * @param failuresBeforeCritical
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class HttpCheck(
  name: String,
  http: String,
  tlsSkipVerify: Boolean,
  interval: String,
  timeout: String,
  header: Option[Map[String, List[String]]] = None,
  method: Option[String] = None,
  body: Option[String] = None,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  successBeforePassing: Int = 0,
  failuresBeforeCritical: Int = 0,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param name - Specifies the name of the check.
 * @param tcp - Specifies a TCP to connect against the value of TCP (expected to be an IP or hostname plus port combination) every Interval. If the connection attempt is successful, the check is passing. If the connection attempt is unsuccessful, the check is critical. In the case of a hostname that resolves to both IPv4 and IPv6 addresses, an attempt will be made to both addresses, and the first successful connection attempt will result in a successful check.
 * @param interval - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param timeout - Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of "10s" or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param successBeforePassing
 * @param failuresBeforeCritical
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class TCPCheck(
  name: String,
  tcp: String,
  interval: String,
  timeout: String,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  successBeforePassing: Int = 0,
  failuresBeforeCritical: Int = 0,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param name - Specifies the name of the check.
 * @param ttl - Specifies this is a TTL check, and the TTL endpoint must be used periodically to update the state of the check.
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class TTLCheck(
  name: String,
  ttl: String,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param name - Specifies the name of the check.
 * @param dockerContainerId - Specifies that the check is a Docker check, and Consul will evaluate the script every Interval in the given container using the specified Shell. Note that Shell is currently only supported for Docker checks.
 * @param shell
 * @param args - Specifies command arguments to run to update the status of the check.
 * @param interval - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param successBeforePassing
 * @param failuresBeforeCritical
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class DockerCheck(
  name: String,
  dockerContainerId: String,
  shell: String,
  args: List[String],
  interval: Option[String] = None,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  successBeforePassing: Int = 0,
  failuresBeforeCritical: Int = 0,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param name - Specifies the name of the check.
 * @param grpc - Specifies a gRPC check's endpoint that supports the standard gRPC health checking protocol. The state of the check will be updated at the given Interval by probing the configured endpoint. Add the service identifier after the gRPC check's endpoint in the following format to check for a specific service instead of the whole gRPC server /:service_identifier.
 * @param grpcUseTLS - Specifies whether to use TLS for this gRPC health check. If TLS is enabled, then by default, a valid TLS certificate is expected. Certificate verification can be turned off by setting TLSSkipVerify to true.
 * @param interval - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param serviceId - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param status - Specifies the initial status of the health check.
 * @param notes - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param successBeforePassing
 * @param failuresBeforeCritical
 * @param deregisterCriticalServiceAfter - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class GRpcCheck(
  name: String,
  grpc: String,
  grpcUseTLS: Boolean,
  interval: Option[String] = None,
  id: Option[String] = None,
  serviceId: Option[String] = None,
  status: CheckStatus = CheckStatus.Critical,
  notes: Option[String] = None,
  successBeforePassing: Int = 0,
  failuresBeforeCritical: Int = 0,
  deregisterCriticalServiceAfter: Option[String] = None
) extends Check

/**
 * @param id - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for uniqueness.
 * @param aliasNode - Specifies the ID of the node for an alias check. If no service is specified, the check will alias the health of the node. If a service is specified, the check will alias the specified service on this particular node.
 * @param aliasService - Specifies the ID of a service for an alias check. If the service is not registered with the same agent, AliasNode must also be specified. Note this is the service ID and not the service name (though they are very often the same).
 */
final case class AliasCheck(id: String, aliasNode: Option[String] = None, aliasService: Option[String] = None) extends Check
