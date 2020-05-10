package consul4s

import sttp.client._
import consul4s.model.KeyValue

trait JsonDecoder {
  def asBoolean: ResponseAs[Boolean, Nothing]

  def asKeyValueOptionList: ResponseAs[Option[List[KeyValue]], Nothing]

  def asString: ResponseAs[String, Nothing]

  def asStringList: ResponseAs[List[String], Nothing]

}
