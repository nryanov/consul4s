package consul4s.model.catalog

final case class Node(
  ID: String,
  Node: String,
  Address: String,
  Datacenter: String,
  TaggedAddresses: Map[String, String],
  Meta: Map[String, String],
  CreateIndex: Long,
  ModifyIndex: Long
)
