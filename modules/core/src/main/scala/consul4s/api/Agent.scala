package consul4s.api

import consul4s.model.CheckStatus
import consul4s.model.agent.{Check, CheckInfo, CheckUpdate, MemberInfo, Service}
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>
  // GET	/agent/members
  def agentMembers(wan: Option[Boolean] = None): F[Result[List[MemberInfo]]] = {
    val wanParam = if (wan.getOrElse(false)) "wan" else ""

    val requestTemplate = basicRequest.get(uri"$url/agent/members?$wanParam")
    val request = requestTemplate.copy(response = jsonDecoder.asMemberInfoList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/agent/checks
  def agentCheckList(filter: Option[String] = None): F[Result[Map[String, CheckInfo]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/checks?$filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asCheckInfoMap)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/register
  def agentRegisterCheck(check: Check): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/register")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.checkToJson(check))

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/deregister/:check_id
  def agentDeregisterCheck(checkId: String): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/deregister/$checkId")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/pass/:check_id
  def agentTTLCheckPass(checkId: String, note: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/pass/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/warn/:check_id
  def agentTTLCheckWarn(checkId: String, note: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/warn/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/fail/:check_id
  def agentTTLCheckFail(checkId: String, note: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/fail/$checkId?note=$note")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/check/update/:check_id
  def agentTTLCheckUpdate(checkId: String, checkUpdate: CheckUpdate): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/check/update/$checkId")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.checkUpdateToJson(checkUpdate))

    val response = sttpBackend.send(request)
    response
  }

  // GET	/agent/services
  def agentServices(filter: Option[String] = None): F[Result[Map[String, Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/check/services?filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceMap)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/agent/service/:service_id
  def agentService(serviceId: String): F[Result[Option[Service]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/check/service/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceOption)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/agent/health/service/name/:service_name
  def agentLocalServiceHealthByName(serviceName: String): F[Result[Option[Map[CheckStatus, List[Service]]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/name/$serviceName")
    val request = requestTemplate.copy(response = jsonDecoder.asCheckStatusServiceMapOption)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/agent/health/service/id/:service_id
  def agentLocalServiceHealthById(serviceId: String): F[Result[Option[Map[CheckStatus, List[Service]]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/health/service/id/$serviceId")
    val request = requestTemplate.copy(response = jsonDecoder.asCheckStatusServiceMapOption)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/service/register
  def agentRegisterLocalService(service: Service, replaceExistingChecks: Boolean = false): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/register?replace-existing-checks=$replaceExistingChecks")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.serviceToJson(service))

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/service/deregister/:service_id
  def agentDeregisterService(serviceId: String): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/agent/service/deregister/$serviceId")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }

  // PUT	/agent/service/maintenance/:service_id
  def agentEnableMaintenanceMode(serviceId: String, enable: Boolean, reason: Option[String] = None): F[Result[Unit]] = {
    val requestTemplate = basicRequest.get(uri"$url/agent/service/maintenance/$serviceId?enable=$enable&reason=$reason")
    val request = requestTemplate.copy(response = asResultUnit)

    val response = sttpBackend.send(request)
    response
  }
}
