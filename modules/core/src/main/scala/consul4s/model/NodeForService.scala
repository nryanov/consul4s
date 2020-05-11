package consul4s.model

final case class NodeForService(node: NodeInfo, serviceInfo: ServiceInfo, checks: List[ServiceCheck])
