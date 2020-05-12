package consul4s.json4s.model

import consul4s.model.{ServiceKind, Status}
import org.json4s.JsonAST.JString
import org.json4s.CustomSerializer

trait Common {
  val statusSerializer = new CustomSerializer[Status](
    implicit format =>
      (
        {
          case JString(value) => Status.withValue(value)
        }, {
          case status: Status => JString(status.value)
        }
      )
  )

  val serviceKindSerializer = new CustomSerializer[ServiceKind](
    implicit format =>
      (
        {
          case JString(value) => ServiceKind.withValue(value)
        }, {
          case status: ServiceKind => JString(status.value)
        }
      )
  )
}
