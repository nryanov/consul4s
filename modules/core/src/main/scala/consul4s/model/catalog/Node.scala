package consul4s.model.catalog

final case class Node(
  id: String,
  node: String,
  address: String,
  datacenter: String,
  taggedAddresses: Map[String, String],
  meta: Map[String, String],
  createIndex: Long,
  modifyIndex: Long
)
