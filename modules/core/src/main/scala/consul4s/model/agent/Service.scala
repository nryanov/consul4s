package consul4s.model.agent

/**
 * @param Service
 *   - Specifies the logical name of the service. Many service instances may share the same logical service name. We recommend using valid
 *     DNS labels for compatibility with external DNS.
 * @param ID
 *   - Specifies a unique ID for this service. This must be unique per agent. This defaults to the Name parameter if not provided.
 * @param Tags
 *   - Specifies a list of tags to assign to the service. These tags can be used for later filtering and are exposed via the APIs. We
 *     recommend using valid DNS labels for compatibility with external DNS
 * @param Address
 *   - Specifies the address of the service. If not provided, the agent's address is used as the address for the service during DNS queries.
 * @param TaggedAddresses
 *   - Specifies a map of explicit LAN and WAN addresses for the service instance. Both the address and port can be specified within the map
 *     values.
 * @param Meta
 *   - Specifies arbitrary KV metadata linked to the service instance.
 * @param Port
 *   - Specifies the port of the service.
 * @param EnableTagOverride
 *   - Specifies to disable the anti-entropy feature for this service's tags. If EnableTagOverride is set to true then external agents can
 *     update this service in the catalog and modify the tags. Subsequent local sync operations by this agent will ignore the updated tags.
 *     For instance, if an external agent modified both the tags and the port for this service and EnableTagOverride was set to true then
 *     after the next sync cycle the service's port would revert to the original value but the tags would maintain the updated value. As a
 *     counter example, if an external agent modified both the tags and port for this service and EnableTagOverride was set to false then
 *     after the next sync cycle the service's port and the tags would revert to the original value and all modifications would be lost.
 * @param Weights
 *   - Specifies weights for the service. Please see the service documentation for more information about weights. If this field is not
 *     provided weights will default to {"Passing": 1, "Warning": 1}.
 */
final case class Service(
  Service: String,
  ID: String,
  Tags: Option[List[String]],
  Address: String,
  TaggedAddresses: Option[Map[String, TaggedAddress]],
  Meta: Option[Map[String, String]],
  Port: Int,
  EnableTagOverride: Boolean,
  Weights: Weights
)
