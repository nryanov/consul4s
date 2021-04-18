package consul4s.model.catalog

import consul4s.model.health.NewHealthCheck

/**
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
 * @param Check(s) - Specifies to register a check. The register API manipulates the health check entry in the Catalog, but it does not setup the script, TTL, or HTTP check to monitor the node's health. To truly enable a new health check, the check must either be provided in agent configuration or set via the agent endpoint.
 *                       The CheckID can be omitted and will default to the value of Name. As with Service.ID, the CheckID must be unique on this node. Notes is an opaque field that is meant to hold human-readable text. If a ServiceID is provided that matches the ID of a service on that node, the check is treated as a service level health check, instead of a node level health check. The Status must be one of passing, warning, or critical.
 *                       The Definition field can be provided with details for a TCP or HTTP health check. For more information, see the Health Checks page.
 *                       Multiple checks can be provided by replacing Check with Checks and sending an array of Check objects.
 */
final case class NodeRegistration(
  Node: String,
  Address: String,
  Service: Option[NewCatalogService] = None,
  Check: Option[NewHealthCheck] = None,
  Checks: Option[List[NewHealthCheck]] = None,
  ID: Option[String] = None,
  Datacenter: Option[String] = None,
  TaggedAddresses: Option[Map[String, String]] = None,
  NodeMeta: Option[Map[String, String]] = None,
  SkipNodeUpdate: Boolean = false
)
