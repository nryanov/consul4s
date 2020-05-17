package consul4s.model.catalog

import consul4s.model.agent.Service

final case class NodeServiceMap(Node: Node, Services: Map[String, Service])
