package consul4s.model.catalog

final case class Node(
  node: String,
  address: String,
  id: Option[String],
  datacenter: Option[String],
  taggedAddresses: Option[Map[String, String]],
  meta: Option[Map[String, String]]
)
