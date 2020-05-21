package consul4s.model.catalog

import consul4s.model.agent.Service

//todo: return Option[NodeServiceList] instead of option fields inside
final case class NodeServiceList(node: Option[Node], services: Option[List[Service]])
