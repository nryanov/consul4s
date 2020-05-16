package consul4s.model.agent

import consul4s.model.CheckStatus

final case class CheckUpdate(Status: CheckStatus, Output: Option[String])
