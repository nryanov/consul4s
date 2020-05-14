package consul4s.model.catalog

final case class NodeServiceList(Node: Option[Node], Services: Option[List[Service]])
