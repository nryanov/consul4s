package consul4s.model.transaction

import consul4s.model.agent.AgentService

final case class ServiceTxnOp(verb: ServiceOp, node: String, service: AgentService)
