package consul4s.json4s.model

import consul4s.model.event._
import org.json4s.FieldSerializer
import org.json4s.FieldSerializer._

trait Event {
  val userEventFormat = FieldSerializer[UserEvent](
    Map(),
    renameFrom("ID", "id")
      .orElse(renameFrom("Name", "name"))
      .orElse(renameFrom("Payload", "payload"))
      .orElse(renameFrom("NodeFilter", "nodeFilter"))
      .orElse(renameFrom("ServiceFilter", "serviceFilter"))
      .orElse(renameFrom("TagFilter", "tagFilter"))
      .orElse(renameFrom("Version", "version"))
      .orElse(renameFrom("LTime", "lTime"))
  )

  val eventAllFieldSerializers = List(userEventFormat)
}
