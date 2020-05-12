package consul4s.model.deprecated

final case class NodeForService(node: NodeInfo, serviceInfo: ServiceInfo, checks: List[ServiceCheck])
