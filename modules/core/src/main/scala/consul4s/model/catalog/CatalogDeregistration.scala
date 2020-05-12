package consul4s.model.catalog

final case class CatalogDeregistration(
  node: String,
  address: Option[String],
  datacenter: String,
  serviceId: String,
  checkId: String,
  namespace: Option[String]
)
