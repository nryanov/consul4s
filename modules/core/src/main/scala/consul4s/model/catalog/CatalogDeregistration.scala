package consul4s.model.catalog

/**
 * If only node is specified then all checks and services on this node will be removed
 * @param node - node id
 * @param datacenter - datacenter name
 * @param serviceId - service name to remove
 * @param checkId - check id to remove
 */
final case class CatalogDeregistration(
  node: String,
  datacenter: Option[String] = None,
  serviceId: Option[String] = None,
  checkId: Option[String] = None
)
