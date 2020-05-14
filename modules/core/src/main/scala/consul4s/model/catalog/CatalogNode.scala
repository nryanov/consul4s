package consul4s.model.catalog

import consul4s.model.agent.AgentService

/*
If node does not exist response will be:
{
    "Node": null,
    "Services": null
}
 */
final case class CatalogNode(Node: Option[Node], Services: Option[Map[String, AgentService]])
