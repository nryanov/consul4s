package consul4s.json4s.model

import consul4s.model.{CheckStatus, ServiceKind, SessionBehavior}
import org.json4s._
import org.json4s.JsonAST.JString

trait Common {
  class CheckStatusSerializer
      extends CustomSerializer[CheckStatus](implicit format =>
        (
          { case JString(value) =>
            CheckStatus.withValue(value)
          },
          { case op: CheckStatus =>
            JString(op.value)
          }
        )
      )

  class ServiceKindSerializer
      extends CustomSerializer[ServiceKind](implicit format =>
        (
          { case JString(value) =>
            ServiceKind.withValue(value)
          },
          { case op: ServiceKind =>
            JString(op.value)
          }
        )
      )

  class SessionBehaviorSerializer
      extends CustomSerializer[SessionBehavior](implicit format =>
        (
          { case JString(value) =>
            SessionBehavior.withValue(value)
          },
          { case op: SessionBehavior =>
            JString(op.value)
          }
        )
      )

  class CheckStatusKeySerializer
      extends CustomKeySerializer[CheckStatus](implicit format =>
        (
          { case value: String =>
            CheckStatus.withValue(value)
          },
          { case op: CheckStatus =>
            op.value
          }
        )
      )

  val commonAllSerializers = List(new CheckStatusSerializer, new ServiceKindSerializer, new SessionBehaviorSerializer)

  val commonAllKeySerializers = List(new CheckStatusKeySerializer)
}
