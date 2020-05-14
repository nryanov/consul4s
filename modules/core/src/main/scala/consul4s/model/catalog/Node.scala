package consul4s.model.catalog

import consul4s.model.agent.TaggedAddress

final case class Node(
  Node: String,
  Address: String,
  ID: Option[String],
  Datacenter: Option[String],
  TaggedAddresses: Option[Map[String, TaggedAddress]],
  Meta: Option[Map[String, String]]
)
