package consul4s.model.catalog

import consul4s.model.agent.AgentService

final case class CatalogNode(node: Option[Node], services: Map[String, AgentService])
