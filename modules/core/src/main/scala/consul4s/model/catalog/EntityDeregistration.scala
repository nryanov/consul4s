package consul4s.model.catalog

/**
 *
 * @param Node - Specifies the ID of the node. If no other values are provided, this node, all its services, and all its checks are removed.
 * @param Datacenter - Specifies the datacenter, which defaults to the agent's datacenter if not provided.
 * @param CheckID - Specifies the ID of the check to remove.
 * @param ServiceID - Specifies the ID of the service to remove. The service and all associated checks will be removed.
 */
final case class EntityDeregistration(
  Node: String,
  Datacenter: Option[String] = None,
  CheckID: Option[String] = None,
  ServiceID: Option[String] = None
)
