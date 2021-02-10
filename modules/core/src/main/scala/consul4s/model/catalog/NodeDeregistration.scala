package consul4s.model.catalog

/**
 * @param node - Specifies the ID of the node. If no other values are provided, this node, all its services, and all its checks are removed.
 * @param datacenter - Specifies the datacenter, which defaults to the agent's datacenter if not provided.
 * @param checkId - Specifies the ID of the check to remove.
 * @param serviceId - Specifies the ID of the service to remove. The service and all associated checks will be removed.
 */
final case class NodeDeregistration(
  node: String,
  datacenter: Option[String] = None,
  checkId: Option[String] = None,
  serviceId: Option[String] = None
)
