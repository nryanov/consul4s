package consul4s.v1.api

import consul4s.model.agent._
import consul4s.model.health.HealthCheck
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>

  /**
   * GET	/agent/members
   * @param wan - Specifies to list WAN members instead of the LAN members (which is the default).
   * This is only eligible for agents running in server mode. This is specified as part of the URL as a query parameter.
   * @param token - consul token
   * @return - This endpoint returns the members the agent sees in the cluster gossip pool.
   */
  def getAgentMembers(wan: Option[Boolean] = None, token: Option[String] = None): F[Result[List[MemberInfo]]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/members?$wanParam")
    val request = requestTemplate.copy(response = jsonDecoder.asMemberInfoList)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/reload
   * This endpoint instructs the agent to reload its configuration.
   * @param token - consul token
   */
  def reloadAgent(token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/reload")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/maintenance
   * This endpoint places the agent into "maintenance mode".
   * During maintenance mode, the node will be marked as unavailable and will not be present in DNS or API queries.
   * This API call is idempotent.
   * @param enable - Specifies whether to enable or disable maintenance mode.
   * This is specified as part of the URL as a query string parameter.
   * @param reason - Specifies a text string explaining the reason for placing the node into maintenance mode.
   * This is simply to aid human operators. If no reason is provided, a default value will be used instead.
   * This is specified as part of the URL as a query string parameter, and, as such, must be URI-encoded.
   * @param token - consul token
   */
  def setAgentMaintenanceMode(enable: Boolean, reason: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/maintenance?enable=$enable&reason=$reason")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/join/:address
   * This endpoint instructs the agent to attempt to connect to a given address.
   * @param address - Specifies the address of the other agent to join. This is specified as part of the URL.
   * @param wan - Specifies to list WAN members instead of the LAN members (which is the default).
   * This is only eligible for agents running in server mode. This is specified as part of the URL as a query parameter.
   * @param token - consul token
   * @return
   */
  def joinAgent(address: String, wan: Option[Boolean] = None, token: Option[String] = None): F[Result[Unit]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/join/$address?$wanParam")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/token/default
   * This endpoint updates the ACL tokens currently in use by the agent.
   * @param newToken - new consul token
   * @param token - consul token
   */
  def updateAgentDefaultToken(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/default")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/agent
   * This endpoint updates the ACL tokens currently in use by the agent.
   * @param newToken - new consul token
   * @param token - consul token
   */
  def updateAgentToken(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/agent_master
   * This endpoint updates the ACL tokens currently in use by the agent.
   * @param newToken - new consul token
   * @param token - consul token
   */
  def updateAgentMasterToken(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent_master")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/replication
   * This endpoint updates the ACL tokens currently in use by the agent.
   * @param newToken - new consul token
   * @param token - consul token
   */
  def updateAgentReplicationToken(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/replication")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * GET	/agent/checks
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param token - consul token
   * @return - This endpoint returns all checks that are registered with the local agent.
   */
  def getAgentChecks(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, HealthCheck]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/checks?$filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckMap)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/register
   * This endpoint adds a new check to the local agent. Checks may be of script, HTTP, TCP, or TTL type.
   * @param check - new agent check
   * @param token - consul token
   */
  def registerAgentCheck(check: Check, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/register")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkToJson(check), token)
  }

  /**
   * PUT	/agent/check/deregister/:check_id
   * This endpoint remove a check from the local agent.
   * @param checkId - check id
   * @param token - consul token
   */
  def deregisterAgentCheck(checkId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/deregister/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/pass/:check_id
   * This endpoint is used with a TTL type check to set the status of the check to passing and to reset the TTL clock.
   * @param checkId - check id
   * @param note - Specifies a human-readable message. This will be passed through to the check's Output field.
   * @param token - consul token
   */
  def setAgentTtlCheckPass(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/pass/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/warn/:check_id
   * This endpoint is used with a TTL type check to set the status of the check to warning and to reset the TTL clock.
   * @param checkId - check id
   * @param note - Specifies a human-readable message. This will be passed through to the check's Output field.
   * @param token - consul token
   */
  def setAgentTtlCheckWarn(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/warn/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/fail/:check_id
   * This endpoint is used with a TTL type check to set the status of the check to critical and to reset the TTL clock.
   * @param checkId - check id
   * @param note - Specifies a human-readable message. This will be passed through to the check's Output field.
   * @param token - consul token
   */
  def setAgentTtlCheckFail(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/fail/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/update/:check_id
   * This endpoint is used with a TTL type check to set the status of the check and to reset the TTL clock.
   * @param checkId - Specifies the unique ID of the check to use. This is specified as part of the URL.
   * @param checkUpdate - check updates
   * @param token - consul token
   */
  def updateAgentTtlCheck(checkId: String, checkUpdate: CheckUpdate, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/update/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkUpdateToJson(checkUpdate), token)
  }

  /**
   * GET	/agent/services
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param token - consul token
   * @return - This endpoint returns all the services that are registered with the local agent.
   */
  def getAgentServices(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/services?filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceMap)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/service/:service_id
   * @param serviceId - service id
   * @param token - consul token
   * @return - This endpoint returns the full service definition for a single service instance registered on the local agent.
   */
  def getAgentService(serviceId: String, token: Option[String] = None): F[Result[Option[Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/service/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceOption)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/health/service/name/:service_name
   * @param serviceName - service name
   * @param token - consul token
   * @return - Retrieve an aggregated state of service(s) on the local agent by name.
   */
  def getAgentLocalServiceHealthByName(
    serviceName: String,
    token: Option[String] = None
  ): F[Result[Option[List[AggregatedServiceStatus]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/name/$serviceName")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusListOption)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/health/service/id/:service_id
   * @param serviceId - service id
   * @param token - consul token
   * @return - Retrieve an aggregated state of service(s) on the local agent by ID.
   */
  def getAgentLocalServiceHealthById(serviceId: String, token: Option[String] = None): F[Result[Option[AggregatedServiceStatus]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/id/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusOption)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/service/register
   * This endpoint adds a new service, with optional health checks, to the local agent.
   * @param service - service definition
   * @param replaceExistingChecks - Missing healthchecks from the request will be deleted from the agent.
   * Using this parameter allows to idempotently register a service and its checks without having to manually deregister checks.
   * @param token - consul token
   */
  def registerAgentService(
    service: NewService,
    replaceExistingChecks: Boolean = false,
    token: Option[String] = None
  ): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/register?replace-existing-checks=$replaceExistingChecks")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.newServiceToJson(service), token)
  }

  /**
   * PUT	/agent/service/deregister/:service_id
   * This endpoint removes a service from the local agent. If the service does not exist, no action is taken.
   * @param serviceId - service id
   * @param token - consul token
   */
  def deregisterAgentService(serviceId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/deregister/$serviceId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/service/maintenance/:service_id
   * This endpoint places a given service into "maintenance mode".
   * During maintenance mode, the service will be marked as unavailable and will not be present in DNS or API queries.
   * This API call is idempotent. Maintenance mode is persistent and will be automatically restored on agent restart.
   * @param serviceId
   * @param enable - Specifies whether to enable or disable maintenance mode.
   * This is specified as part of the URL as a query string parameter.
   * @param reason - Specifies a text string explaining the reason for placing the node into maintenance mode.
   * This is simply to aid human operators. If no reason is provided, a default value will be used instead.
   * This is specified as part of the URL as a query string parameter, and, as such, must be URI-encoded.
   * @param token - consul token
   */
  def setAgentServiceMaintenanceMode(
    serviceId: String,
    enable: Boolean,
    reason: Option[String] = None,
    token: Option[String] = None
  ): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/maintenance/$serviceId?enable=$enable&reason=$reason")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }
}
