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
    override def asBooleanUnsafe: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlways[List[KeyValue]].map(_.toOption)

    override def asStringUnsafe: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringListUnsafe: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]

    override def asStringOption: ResponseAs[Option[String], Nothing] = asJsonAlways[String].map(_.toOption)

    override def asStringListOption: ResponseAs[Option[List[String]], Nothing] = asJsonAlways[List[String]].map(_.toOption)
  }
}
