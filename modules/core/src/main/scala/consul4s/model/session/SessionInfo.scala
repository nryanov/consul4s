package consul4s.model.session

import consul4s.model.SessionBehavior

final case class SessionInfo(
  ID: String,
  Name: String,
  Node: String,
  LockDelay: Long,
  CreateIndex: Long,
  ModifyIndex: Long,
  ServiceChecks: Option[List[String]],
  NodeChecks: Option[List[String]],
  Behavior: SessionBehavior,
  TTL: String
)
