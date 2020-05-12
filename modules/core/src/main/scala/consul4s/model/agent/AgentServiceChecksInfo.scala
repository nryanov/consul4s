package consul4s.model.agent

import consul4s.model.health.HealthCheck

final case class AgentServiceChecksInfo(aggregatedStatus: String, service: AgentService, checks: List[HealthCheck])
