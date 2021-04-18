package consul4s.model.catalog

import consul4s.model.ServiceKind
import consul4s.model.agent.{TaggedAddress, Weights}

// todo: Proxy & Connect
final case class CatalogService(
  ID: String,
  Node: String,
  Address: String,
  Datacenter: String,
  TaggedAddresses: Option[Map[String, String]],
  NodeMeta: Option[Map[String, String]],
  ServiceKind: ServiceKind,
  ServiceID: String,
  ServiceName: String,
  ServiceTags: Option[List[String]],
  ServiceAddress: String,
  ServiceTaggedAddresses: Option[Map[String, TaggedAddress]],
  ServiceWeights: Weights,
  ServiceMeta: Option[Map[String, String]],
  ServicePort: Int,
  ServiceEnableTagOverride: Boolean
)
