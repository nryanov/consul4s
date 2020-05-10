package consul4s.model

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
