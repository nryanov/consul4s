package consul4s.model.agent

import consul4s.model.CheckStatus

/**
 *
 * @param Status - Specifies the status of the check. Valid values are "passing", "warning", and "critical".
 * @param Output - Specifies a human-readable message. This will be passed through to the check's Output field.
 */
final case class CheckUpdate(Status: CheckStatus, Output: Option[String])
