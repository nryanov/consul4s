package consul4s.model.health

import consul4s.model.agent.AgentService
import consul4s.model.catalog.Node

final case class ServiceEntry(node: Node, service: AgentService, checks: List[HealthCheck])
