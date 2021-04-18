package consul4s.model.health

import consul4s.model.agent._
import consul4s.model.catalog.Node

final case class ServiceEntry(Node: Node, Service: Service, Checks: List[HealthCheck])
