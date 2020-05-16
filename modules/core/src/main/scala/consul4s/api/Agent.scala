package consul4s.api

import consul4s.model.agent.{Check, CheckInfo, CheckUpdate, MemberInfo}
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
}
