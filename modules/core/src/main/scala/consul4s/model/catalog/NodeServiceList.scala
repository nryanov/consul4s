package consul4s.model.catalog

import consul4s.model.agent.Service

final case class NodeServiceList(Node: Option[Node], Services: Option[List[Service]])
