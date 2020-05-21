package consul4s.model.agent

import consul4s.model.CheckStatus

/**
 *
 * @param status - Specifies the status of the check. Valid values are "passing", "warning", and "critical".
 * @param output - Specifies a human-readable message. This will be passed through to the check's Output field.
 */
final case class CheckUpdate(status: CheckStatus, output: Option[String])
