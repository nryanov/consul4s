package consul4s.model.catalog

import consul4s.model.agent.{TaggedAddress, Weights}

/**
 * @param service - Specifies the logical name of the service.
 *             Many service instances may share the same logical service name.
 *             We recommend using valid DNS labels for compatibility with external DNS.
 * @param id - Specifies a unique ID for this service. This must be unique per agent. This defaults to the Name parameter if not provided.
 * @param tags - Specifies a list of tags to assign to the service. These tags can be used for later filtering and are exposed via the APIs. We recommend using valid DNS labels for compatibility with external DNS
 * @param address - Specifies the address of the service. If not provided, the agent's address is used as the address for the service during DNS queries.
 * @param taggedAddresses - Specifies a map of explicit LAN and WAN addresses for the service instance. Both the address and port can be specified within the map values.
 * @param meta - Specifies arbitrary KV metadata linked to the service instance.
 * @param port - Specifies the port of the service.
 * @param enableTagOverride - Specifies to disable the anti-entropy feature for this service's tags.
 *                          If EnableTagOverride is set to true then external agents can update this service
 *                          in the catalog and modify the tags. Subsequent local sync operations by this agent will
 *                          ignore the updated tags. For instance, if an external agent modified both the tags and the
 *                          port for this service and EnableTagOverride was set to true then after the next sync cycle
 *                          the service's port would revert to the original value but the tags would maintain the updated value.
 *                          As a counter example, if an external agent modified both the tags and port for this service
 *                          and EnableTagOverride was set to false then after the next sync cycle the service's port and
 *                          the tags would revert to the original value and all modifications would be lost.
 * @param weights - Specifies weights for the service. Please see the service documentation for more information about weights.
 *                If this field is not provided weights will default to {"Passing": 1, "Warning": 1}.
 */
final case class NewCatalogService(
  service: String,
  id: Option[String] = None,
  tags: Option[List[String]] = None,
  address: Option[String] = None,
  taggedAddresses: Option[Map[String, TaggedAddress]] = None,
  meta: Option[Map[String, String]] = None,
  port: Option[Int] = None,
  enableTagOverride: Boolean = false,
  weights: Option[Weights] = None
)
