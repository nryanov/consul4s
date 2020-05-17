package consul4s.model.catalog

/**
 *
 * @param Node - Specifies the node ID to register.
 * @param Address - Specifies the address to register.
 * @param Service - Specifies to register a service.
 *                If ID is not provided, it will be defaulted to the value of the Service.Service property.
 *                Only one service with a given ID may be present per node.
 *                We recommend using valid DNS labels for service definition names for compatibility with external DNS.
 *                The service Tags, Address, Meta, and Port fields are all optional.
 * @param ID - An optional UUID to assign to the node. This must be a 36-character UUID-formatted string.
 * @param Datacenter - Specifies the datacenter, which defaults to the agent's datacenter if not provided.
 * @param TaggedAddresses - Specifies the tagged addresses.
 * @param NodeMeta - Specifies arbitrary KV metadata pairs for filtering purposes.
 * @param SkipNodeUpdate - Specifies whether to skip updating the node's information in the registration.
 *                       This is useful in the case where only a health check or
 *                       service entry on a node needs to be updated or when a register request is intended to update a
 *                       service entry or health check. In both use cases, node information will not be overwritten,
 *                       if the node is already registered. Note, if the parameter is enabled for a node that doesn't exist,
 *                       it will still be created.
 */
// todo: add Check field: https://www.consul.io/api-docs/catalog#parameters
final case class EntityRegistration(
  Node: String,
  Address: String,
  Service: Option[NewCatalogService] = None,
  ID: Option[String] = None,
  Datacenter: Option[String] = None,
  TaggedAddresses: Option[Map[String, String]] = None,
  NodeMeta: Option[Map[String, String]] = None,
  SkipNodeUpdate: Boolean = false
)
