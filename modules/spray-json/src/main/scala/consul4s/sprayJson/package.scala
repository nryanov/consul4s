package consul4s

import consul4s.model.KeyValue
import sttp.client.ResponseAs
import sttp.client.sprayJson._
import spray.json._

package object sprayJson {
  private object JsonProtocol extends DefaultJsonProtocol {
    implicit val keyValueFormat = jsonFormat(KeyValue.apply, "CreateIndex", "ModifyIndex", "LockIndex", "Key", "Value", "Flags")
  }

  import JsonProtocol._

  implicit val sprayJsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asKeyValueOptionList: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlwaysUnsafe[Option[List[KeyValue]]]

    override def asString: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringList: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]
  }
}
