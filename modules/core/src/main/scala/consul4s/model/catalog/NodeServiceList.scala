package consul4s.model.catalog

import consul4s.model.agent.Service

//todo: return Option[NodeServiceList] instead of option fields inside
final case class NodeServiceList(Node: Option[Node], Services: Option[List[Service]])
