package consul4s.v1.api

import consul4s.model.agent._
import consul4s.model.health.HealthCheck
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>

  /**
   * GET	/agent/members
   * @param wan
   * @param token - consul token
   * @return
   */
  def agentMembers(wan: Option[Boolean] = None, token: Option[String] = None): F[Result[List[MemberInfo]]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/members?$wanParam")
    val request = requestTemplate.copy(response = jsonDecoder.asMemberInfoList)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/reload
   * @param token - consul token
   * @return
   */
  def agentReload(token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/reload")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/maintenance
   * @param enable
   * @param reason
   * @param token - consul token
   * @return
   */
  def agentEnableMaintenance(enable: Boolean, reason: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/maintenance?enable=$enable&reason=$reason")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/join/:addres
   * @param address
   * @param wan
   * @param token - consul token
   * @return
   */
  def agentJoin(address: String, wan: Option[Boolean] = None, token: Option[String] = None): F[Result[Unit]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/join/$address?$wanParam")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/token/default
   * @param newToken
   * @param token - consul token
   * @return
   */
  def agentUpdateACLTokenDefault(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/default")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/agent
   * @param newToken
   * @param token - consul token
   * @return
   */
  def agentUpdateACLTokenAgent(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/agent_master
   * @param newToken
   * @param token - consul token
   * @return
   */
  def agentUpdateACLTokenMaster(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent_master")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * PUT	/agent/token/replication
   * @param newToken
   * @param token - consul token
   * @return
   */
  def agentUpdateACLTokenReplication(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/replication")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  /**
   * GET	/agent/checks
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param token - consul token
   * @return
   */
  def agentCheckList(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, HealthCheck]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/checks?$filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckMap)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/register
   * @param check
   * @param token - consul token
   * @return
   */
  def agentRegisterCheck(check: Check, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/register")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkToJson(check), token)
  }

  /**
   * PUT	/agent/check/deregister/:check_id
   * @param checkId
   * @param token - consul token
   * @return
   */
  def agentDeregisterCheck(checkId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/deregister/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/pass/:check_id
   * @param checkId
   * @param note
   * @param token - consul token
   * @return
   */
  def agentTTLCheckPass(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/pass/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/warn/:check_id
   * @param checkId
   * @param note
   * @param token - consul token
   * @return
   */
  def agentTTLCheckWarn(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/warn/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/fail/:check_id
   * @param checkId
   * @param note
   * @param token - consul token
   * @return
   */
  def agentTTLCheckFail(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/fail/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/check/update/:check_id
   * @param checkId
   * @param checkUpdate
   * @param token - consul token
   * @return
   */
  def agentTTLCheckUpdate(checkId: String, checkUpdate: CheckUpdate, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/update/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkUpdateToJson(checkUpdate), token)
  }

  /**
   * GET	/agent/services
   * @param filter - Specifies the expression used to filter the queries results prior to returning the data.
   * @param token - consul token
   * @return
   */
  def agentServices(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/services?filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceMap)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/service/:service_id
   * @param serviceId
   * @param token - consul token
   * @return
   */
  def agentService(serviceId: String, token: Option[String] = None): F[Result[Option[Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/service/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceOption)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/health/service/name/:service_name
   * @param serviceName
   * @param token - consul token
   * @return
   */
  def agentLocalServiceHealthByName(serviceName: String, token: Option[String] = None): F[Result[Option[List[AggregatedServiceStatus]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/name/$serviceName")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusListOption)

    sendRequest(request, token)
  }

  /**
   * GET	/agent/health/service/id/:service_id
   * @param serviceId
   * @param token - consul token
   * @return
   */
  def agentLocalServiceHealthById(serviceId: String, token: Option[String] = None): F[Result[Option[AggregatedServiceStatus]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/id/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusOption)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/service/register
   * @param service
   * @param replaceExistingChecks
   * @param token - consul token
   * @return
   */
  def agentRegisterLocalService(
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
   * @param serviceId
   * @param token - consul token
   * @return
   */
  def agentDeregisterService(serviceId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/deregister/$serviceId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  /**
   * PUT	/agent/service/maintenance/:service_id
   * @param serviceId
   * @param enable
   * @param reason
   * @param token - consul token
   * @return
   */
  def agentEnableMaintenanceMode(
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
