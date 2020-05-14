package consul4s.model.agent

final case class Upstream(
  DestinationType: Option[UpstreamDestType],
  DestinationNamespace: Option[String],
  DestinationName: String,
  Datacenter: Option[String],
  LocalBindAddress: Option[String],
  LocalBindPort: Option[Int],
  Config: Option[Map[String, String]]
)
