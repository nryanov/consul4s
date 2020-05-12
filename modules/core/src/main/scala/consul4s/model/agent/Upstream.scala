package consul4s.model.agent

final case class Upstream(
  destinationType: Option[UpstreamDestType],
  destinationNamespace: Option[String],
  destinationName: String,
  datacenter: Option[String],
  localBindAddress: Option[String],
  localBindPort: Option[Int],
  config: Option[Map[String, String]]
  // todo:
//  meshGateway: Option[MeshGatewayConfig]
)
