package consul4s.model.health

import consul4s.model.agent._
import consul4s.model.catalog.Node

final case class ServiceEntry(node: Node, service: Service, checks: List[HealthCheck])
