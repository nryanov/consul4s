package consul4s.model.catalog

import consul4s.model.agent.Service

final case class NodeServiceList(node: Node, services: List[Service])

object NodeServiceList {
  final case class NodeServiceListInternal(node: Option[Node], services: Option[List[Service]])
}
