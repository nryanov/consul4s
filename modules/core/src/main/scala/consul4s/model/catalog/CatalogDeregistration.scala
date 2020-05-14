package consul4s.model.catalog

/**
 * If only node is specified then all checks and services on this node will be removed
 * @param Node - node id
 * @param Datacenter - datacenter name
 * @param ServiceId - service name to remove
 * @param CheckId - check id to remove
 */
final case class CatalogDeregistration(
  Node: String,
  Datacenter: Option[String] = None,
  ServiceId: Option[String] = None,
  CheckId: Option[String] = None
)
