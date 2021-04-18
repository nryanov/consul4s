package consul4s.model.catalog

import consul4s.model.agent.Service

final case class NodeServiceList(Node: Node, Services: List[Service])

object NodeServiceList {
  final case class NodeServiceListInternal(Node: Option[Node], Services: Option[List[Service]])
}
