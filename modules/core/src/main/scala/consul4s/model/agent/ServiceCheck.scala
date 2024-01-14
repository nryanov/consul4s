package consul4s.model.agent

import consul4s.model.CheckStatus

/**
 * https://www.consul.io/docs/agent/checks
 */
sealed trait ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param Args
 *   - Specifies command arguments to run to update the status of the check.
 * @param Timeout
 *   - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param Interval
 *   - Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of "10s"
 *     or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceScriptCheck(
  Name: String,
  Args: List[String],
  Timeout: Option[String] = None,
  Interval: Option[String] = None,
  CheckID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param HTTP
 *   - Specifies an HTTP check to perform a GET request against the value of HTTP (expected to be a URL) every Interval. If the response is
 *     any 2xx code, the check is passing. If the response is 429 Too Many Requests, the check is warning. Otherwise, the check is critical.
 *     HTTP checks also support SSL. By default, a valid SSL certificate is expected. Certificate verification can be controlled using the
 *     TLSSkipVerify.
 * @param TLSSkipVerify
 *   - Specifies if the certificate for an HTTPS check should not be verified.
 * @param Interval
 *   - Specifies a different HTTP method to be used for an HTTP check. When no value is specified, GET is used.
 * @param Timeout
 *   - Specifies a set of headers that should be set for HTTP checks. Each header can have multiple values.
 * @param Header
 *   - Specifies a body that should be sent with HTTP checks.
 * @param Method
 *   - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param Body
 *   - \- Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of
 *     "10s" or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param SuccessBeforePassing
 * @param FailuresBeforeCritical
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceHttpCheck(
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
) extends ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param TCP
 *   - Specifies a TCP to connect against the value of TCP (expected to be an IP or hostname plus port combination) every Interval. If the
 *     connection attempt is successful, the check is passing. If the connection attempt is unsuccessful, the check is critical. In the case
 *     of a hostname that resolves to both IPv4 and IPv6 addresses, an attempt will be made to both addresses, and the first successful
 *     connection attempt will result in a successful check.
 * @param Interval
 *   - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param Timeout
 *   - Specifies a timeout for outgoing connections in the case of a Script, HTTP, TCP, or gRPC check. Can be specified in the form of "10s"
 *     or "5m" (i.e., 10 seconds or 5 minutes, respectively).
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param SuccessBeforePassing
 * @param FailuresBeforeCritical
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceTCPCheck(
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
) extends ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param TTL
 *   - Specifies this is a TTL check, and the TTL endpoint must be used periodically to update the state of the check.
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceTTLCheck(
  Name: String,
  TTL: String,
  CheckID: Option[String] = None,
  ServiceID: Option[String] = None,
  Status: CheckStatus = CheckStatus.Critical,
  Notes: Option[String] = None,
  DeregisterCriticalServiceAfter: Option[String] = None
) extends ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param DockerContainerID
 *   - Specifies that the check is a Docker check, and Consul will evaluate the script every Interval in the given container using the
 *     specified Shell. Note that Shell is currently only supported for Docker checks.
 * @param Shell
 * @param Args
 *   - Specifies command arguments to run to update the status of the check.
 * @param Interval
 *   - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param SuccessBeforePassing
 * @param FailuresBeforeCritical
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceDockerCheck(
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
) extends ServiceCheck

/**
 * @param Name
 *   - Specifies the name of the check.
 * @param GRPC
 *   - Specifies a gRPC check's endpoint that supports the standard gRPC health checking protocol. The state of the check will be updated at
 *     the given Interval by probing the configured endpoint. Add the service identifier after the gRPC check's endpoint in the following
 *     format to check for a specific service instead of the whole gRPC server /:service_identifier.
 * @param GRPCUseTLS
 *   - Specifies whether to use TLS for this gRPC health check. If TLS is enabled, then by default, a valid TLS certificate is expected.
 *     Certificate verification can be turned off by setting TLSSkipVerify to true.
 * @param Interval
 *   - Specifies the frequency at which to run this check. This is required for HTTP and TCP checks.
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param ServiceID
 *   - Specifies the ID of a service to associate the registered check with an existing service provided by the agent.
 * @param Status
 *   - Specifies the initial status of the health check.
 * @param Notes
 *   - Specifies arbitrary information for humans. This is not used by Consul internally.
 * @param SuccessBeforePassing
 * @param FailuresBeforeCritical
 * @param DeregisterCriticalServiceAfter
 *   - Specifies that checks associated with a service should deregister after this time. This is specified as a time duration with suffix
 *     like "10m". If a check is in the critical state for more than this configured value, then its associated service (and all of its
 *     associated checks) will automatically be deregistered. The minimum timeout is 1 minute, and the process that reaps critical services
 *     runs every 30 seconds, so it may take slightly longer than the configured timeout to trigger the deregistration. This should
 *     generally be configured with a timeout that's much, much longer than any expected recoverable outage for the given service.
 */
final case class ServiceGrpcCheck(
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
) extends ServiceCheck

/**
 * @param CheckID
 *   - Specifies a unique ID for this check on the node. This defaults to the "Name" parameter, but it may be necessary to provide an ID for
 *     uniqueness.
 * @param AliasNode
 *   - Specifies the ID of the node for an alias check. If no service is specified, the check will alias the health of the node. If a
 *     service is specified, the check will alias the specified service on this particular node.
 * @param AliasService
 *   - Specifies the ID of a service for an alias check. If the service is not registered with the same agent, AliasNode must also be
 *     specified. Note this is the service ID and not the service name (though they are very often the same).
 */
final case class ServiceAliasCheck(CheckID: String, AliasNode: Option[String] = None, AliasService: Option[String] = None)
    extends ServiceCheck
