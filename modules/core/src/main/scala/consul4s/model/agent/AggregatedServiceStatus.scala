package consul4s.model.agent

import consul4s.model.CheckStatus
import consul4s.model.health.HealthCheck

final case class AggregatedServiceStatus(aggregatedStatus: CheckStatus, service: Service, checks: List[HealthCheck])
