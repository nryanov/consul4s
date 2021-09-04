package consul4s.v1.api

import sttp.client3._

trait Status[F[_]] { this: ConsulApi[F] =>

  /**
   * GET /status/leader
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter.
   * @param token
   *   - Consul token
   * @return
   *   - Raft leader for the datacenter in which the agent is running.
   */
  def getRaftLeader(dc: Option[String] = None, token: Option[String] = None): F[Result[String]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/leader?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringValue)

    sendRequest(request, token)
  }

  /**
   * GET /status/peers
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *     URL as a query parameter.
   * @param token
   *   - Consul token
   * @return
   *   - Raft peers for the datacenter in which the the agent is running. This list of peers is strongly consistent and can be useful in
   *     determining when a given server has successfully joined the cluster.
   */
  def getRaftPeers(dc: Option[String] = None, token: Option[String] = None): F[Result[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/status/peers?dc=$dc")
    val request = requestTemplate.copy(response = jsonDecoder.asStringList)

    sendRequest(request, token)
  }
}
