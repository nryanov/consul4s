package consul4s.api

import consul4s.model.agent.{AgentMember, MembersOpts}
import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>
  // GET	/agent/members
  def members(opts: MembersOpts): F[Response[List[AgentMember]]] = {
    // todo: body -> opts
    val requestTemplate = basicRequest.get(uri"$url/agent/members")
    val request = requestTemplate.copy(response = jsonDecoder.asAgentMembersUnsafe)

    val response = sttpBackend.send(request)
    response
  }
}
