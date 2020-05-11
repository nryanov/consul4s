package consul4s.model

final case class NodeInfo(
  id: String,
  node: String,
  address: String,
  datacenter: String,
  taggedAddresses: Map[String, String],
  meta: Map[String, String]
)
