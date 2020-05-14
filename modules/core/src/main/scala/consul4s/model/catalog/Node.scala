package consul4s.model.catalog

final case class Node(
  Node: String,
  Address: String,
  ID: Option[String],
  Datacenter: Option[String],
  TaggedAddresses: Option[Map[String, String]],
  Meta: Option[Map[String, String]]
)
