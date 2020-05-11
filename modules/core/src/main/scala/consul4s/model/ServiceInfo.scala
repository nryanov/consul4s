package consul4s.model

final case class ServiceInfo(
  id: String,
  service: String,
  tags: List[String],
  address: String,
  meta: Map[String, String],
  port: Int,
  weights: Map[String, Int],
  enableTagOverride: Boolean,
  proxy: Map[String, Map[String, String]],
  connect: Map[String, String]
)
