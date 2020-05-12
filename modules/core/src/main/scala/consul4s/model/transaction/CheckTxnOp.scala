package consul4s.model.transaction

import consul4s.model.health.HealthCheck

final case class CheckTxnOp(verb: CheckOp, check: HealthCheck)
