package consul4s.model.deprecated

import consul4s.model.State

final case class ServiceCheck(
  node: String,
  checkId: String,
  name: String,
  status: State,
  notes: String,
  output: String,
  serviceId: String,
  serviceName: String,
  serviceTags: List[String],
  namespace: Option[String]
)
