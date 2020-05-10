package consul4s

import sttp.client._
import consul4s.model.KeyValue

trait JsonDecoder {
  def asBooleanUnsafe: ResponseAs[Boolean, Nothing]

  def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing]

  def asStringUnsafe: ResponseAs[String, Nothing]

  def asStringListUnsafe: ResponseAs[List[String], Nothing]

  def asStringOption: ResponseAs[Option[String], Nothing]

  def asStringListOption: ResponseAs[Option[List[String]], Nothing]

}
