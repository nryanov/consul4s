package consul4s

import consul4s.model.KeyValue
import sttp.client.ResponseAs
import sttp.client.circe._
import io.circe._

package object circe {
  private implicit val keyValueDecoder: Decoder[KeyValue] = new Decoder[KeyValue] {
    final def apply(c: HCursor): Decoder.Result[KeyValue] =
      for {
        createIndex <- c.downField("CreateIndex").as[Long]
        modifyIndex <- c.downField("ModifyIndex").as[Long]
        lockIndex <- c.downField("LockIndex").as[Long]
        key <- c.downField("Key").as[String]
        valueBase64 <- c.downField("Value").as[String]
        flags <- c.downField("Flags").as[Int]
      } yield KeyValue(createIndex, modifyIndex, lockIndex, key, valueBase64, flags)
  }

  implicit val circeJsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asKeyValueOptionList: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlwaysUnsafe[Option[List[KeyValue]]]

    override def asString: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringList: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]
  }
}
