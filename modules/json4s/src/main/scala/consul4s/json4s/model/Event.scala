package consul4s.json4s.model

import consul4s.model.event.UserEvent
import org.json4s.JsonAST.{JArray, JField, JInt, JLong, JString}
import org.json4s.{CustomSerializer, JObject}

trait Event {

  val eventAllSerializers = List()
}
