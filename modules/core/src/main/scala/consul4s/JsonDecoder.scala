package consul4s

import sttp.client._

trait JsonDecoder {
  def asBooleanUnsafe: ResponseAs[Boolean, Nothing]

  def asStringUnsafe: ResponseAs[String, Nothing]

  def asStringListUnsafe: ResponseAs[List[String], Nothing]

  def asStringOption: ResponseAs[Option[String], Nothing]

  def asStringListOption: ResponseAs[Option[List[String]], Nothing]

  def asMapOption: ResponseAs[Option[Map[String, String]], Nothing]

  def asMapUnsafe: ResponseAs[Map[String, String], Nothing]

  def asMapMultipleValuesOption: ResponseAs[Option[Map[String, List[String]]], Nothing]

  def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing]
}
