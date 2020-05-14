package consul4s.model.catalog

final case class NodeServiceMap(Node: Node, Services: Map[String, Service])
