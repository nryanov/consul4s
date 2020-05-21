package consul4s.model.catalog

import consul4s.model.ServiceKind
import consul4s.model.agent.{TaggedAddress, Weights}

// todo: Proxy & Connect
final case class CatalogService(
  id: String,
  node: String,
  address: String,
  datacenter: String,
  taggedAddresses: Option[Map[String, String]],
  nodeMeta: Option[Map[String, String]],
  serviceKind: ServiceKind,
  serviceID: String,
  serviceName: String,
  serviceTags: Option[List[String]],
  serviceAddress: String,
  serviceTaggedAddresses: Option[Map[String, TaggedAddress]],
  serviceWeights: Weights,
  serviceMeta: Option[Map[String, String]],
  servicePort: Int,
  serviceEnableTagOverride: Boolean
)
