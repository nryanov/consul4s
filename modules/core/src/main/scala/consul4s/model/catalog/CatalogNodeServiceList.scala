package consul4s.model.catalog

import consul4s.model.agent.AgentService

final case class CatalogNodeServiceList(node: Option[Node], services: List[AgentService])
