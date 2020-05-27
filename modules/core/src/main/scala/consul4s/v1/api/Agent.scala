package consul4s.v1.api

import consul4s.model.agent._
import consul4s.model.health.HealthCheck
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>
  // GET	/agent/members
  def agentMembers(wan: Option[Boolean] = None, token: Option[String] = None): F[Result[List[MemberInfo]]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/members?$wanParam")
    val request = requestTemplate.copy(response = jsonDecoder.asMemberInfoList)

    sendRequest(request, token)
  }

  // PUT	/agent/reload
  def agentReload(token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/reload")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/maintenance
  def agentEnableMaintenance(enable: Boolean, reason: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/maintenance?enable=$enable&reason=$reason")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/join/:addres
  def agentJoin(address: String, wan: Option[Boolean] = None, token: Option[String] = None): F[Result[Unit]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/join/$address?$wanParam")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/token/default
  def agentUpdateACLTokenDefault(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/default")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  // PUT	/agent/token/agent
  def agentUpdateACLTokenAgent(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  // PUT	/agent/token/agent_master
  def agentUpdateACLTokenMaster(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/agent_master")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  // PUT	/agent/token/replication
  def agentUpdateACLTokenReplication(newToken: Token, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/token/replication")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.tokenAsJson(newToken), token)
  }

  // GET	/agent/checks
  def agentCheckList(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, HealthCheck]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/checks?$filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asHealthCheckMap)

    sendRequest(request, token)
  }

  // PUT	/agent/check/register
  def agentRegisterCheck(check: Check, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/register")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkToJson(check), token)
  }

  // PUT	/agent/check/deregister/:check_id
  def agentDeregisterCheck(checkId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/deregister/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/check/pass/:check_id
  def agentTTLCheckPass(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/pass/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/check/warn/:check_id
  def agentTTLCheckWarn(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/warn/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/check/fail/:check_id
  def agentTTLCheckFail(checkId: String, note: Option[String] = None, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/fail/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/check/update/:check_id
  def agentTTLCheckUpdate(checkId: String, checkUpdate: CheckUpdate, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/update/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.checkUpdateToJson(checkUpdate), token)
  }

  // GET	/agent/services
  def agentServices(filter: Option[String] = None, token: Option[String] = None): F[Result[Map[String, Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/services?filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceMap)

    sendRequest(request, token)
  }

  // GET	/agent/service/:service_id
  def agentService(serviceId: String, token: Option[String] = None): F[Result[Option[Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/service/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceOption)

    sendRequest(request, token)
  }

  // GET	/agent/health/service/name/:service_name
  def agentLocalServiceHealthByName(serviceName: String, token: Option[String] = None): F[Result[Option[List[AggregatedServiceStatus]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/name/$serviceName")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusListOption)

    sendRequest(request, token)
  }

  // GET	/agent/health/service/id/:service_id
  def agentLocalServiceHealthById(serviceId: String, token: Option[String] = None): F[Result[Option[AggregatedServiceStatus]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/id/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asAggregatedServiceStatusOption)

    sendRequest(request, token)
  }

  // PUT	/agent/service/register
  def agentRegisterLocalService(
    service: NewService,
    replaceExistingChecks: Boolean = false,
    token: Option[String] = None
  ): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/register?replace-existing-checks=$replaceExistingChecks")
    val request = requestTemplate.copy(response = asResultUnit)

    saveSendRequest(request, jsonEncoder.newServiceToJson(service), token)
  }

  // PUT	/agent/service/deregister/:service_id
  def agentDeregisterService(serviceId: String, token: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/deregister/$serviceId")
    val request = requestTemplate.copy(response = asResultUnit)

    sendRequest(request, token)
  }

  // PUT	/agent/service/maintenance/:service_id
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
