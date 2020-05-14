package consul4s.model.agent

import consul4s.model.health.HealthCheck

final case class AgentServiceChecksInfo(AggregatedStatus: String, Service: AgentService, Checks: List[HealthCheck])
