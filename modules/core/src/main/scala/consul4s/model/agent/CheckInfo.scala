package consul4s.model.agent

import consul4s.model.CheckStatus

// todo: should we add Type and Definition fields here too?
final case class CheckInfo(
  Node: String,
  CheckID: String,
  Name: String,
  Status: CheckStatus,
  Notes: String,
  Output: String,
  ServiceID: String,
  ServiceName: String,
  ServiceTags: List[String]
)
