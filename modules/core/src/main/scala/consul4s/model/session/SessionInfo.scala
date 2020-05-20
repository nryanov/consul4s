package consul4s.model.session

import consul4s.model.SessionBehavior

final case class SessionInfo(
  id: String,
  name: String,
  node: String,
  lockDelay: Long,
  createIndex: Long,
  modifyIndex: Long,
  serviceChecks: Option[List[String]],
  nodeChecks: Option[List[String]],
  behavior: SessionBehavior,
  ttl: String
)
