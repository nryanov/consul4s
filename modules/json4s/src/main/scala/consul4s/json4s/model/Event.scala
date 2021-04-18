package consul4s.json4s.model

import consul4s.model.event._
import org.json4s.FieldSerializer

trait Event {
  val userEventFormat: FieldSerializer[UserEvent] = FieldSerializer[UserEvent]()

  val eventAllFieldSerializers = List(userEventFormat)
}
