package consul4s

import consul4s.model.KeyValue
import sttp.client.ResponseAs
import sttp.client.json4s._
import org.json4s._
import org.json4s.native.Serialization

package object json4s {
  class KeyValueSerializer
      extends CustomSerializer[KeyValue](
        format =>
          (
            {
              case JObject(
                  JField("CreateIndex", JLong(f1)) ::
                    JField("ModifyIndex", JLong(f2)) ::
                    JField("LockIndex", JLong(f3)) ::
                    JField("Key", JString(f4)) ::
                    JField("Value", JString(f5)) ::
                    JField("Flags", JInt(f6)) ::
                    Nil
                  ) =>
                KeyValue(f1, f2, f3, f4, f5, f6.intValue)
            }, {
              case _: KeyValue => JObject()
            }
          )
      )

  private implicit val serialization = org.json4s.native.Serialization
  implicit val formats = Serialization.formats(NoTypeHints) + new KeyValueSerializer

  implicit val json4sJsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asKeyValueOptionList: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlwaysUnsafe[Option[List[KeyValue]]]

    override def asString: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringList: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]
  }
}
