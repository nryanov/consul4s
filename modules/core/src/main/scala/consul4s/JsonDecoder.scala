package consul4s

import sttp.client._
import consul4s.model.{KeyValue, NodeCheck, ServiceCheck}

trait JsonDecoder {
  def asBooleanUnsafe: ResponseAs[Boolean, Nothing]

  def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing]

  def asNodeChecksOption: ResponseAs[Option[List[NodeCheck]], Nothing]

  def asServiceChecksOption: ResponseAs[Option[List[ServiceCheck]], Nothing]

  def asNodeChecksUnsafe: ResponseAs[List[NodeCheck], Nothing]

  def asServiceChecksUnsafe: ResponseAs[List[ServiceCheck], Nothing]

  def asStringUnsafe: ResponseAs[String, Nothing]

  def asStringListUnsafe: ResponseAs[List[String], Nothing]

  def asStringOption: ResponseAs[Option[String], Nothing]

  def asStringListOption: ResponseAs[Option[List[String]], Nothing]

}
