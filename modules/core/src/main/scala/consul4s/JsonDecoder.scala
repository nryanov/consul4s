package consul4s

import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import sttp.client._

trait JsonDecoder {
  def asBooleanUnsafe: ResponseAs[Boolean, Nothing]

  def asStringUnsafe: ResponseAs[String, Nothing]

  def asStringListUnsafe: ResponseAs[List[String], Nothing]

  def asStringListOption: ResponseAs[Option[List[String]], Nothing]

  def asMapUnsafe: ResponseAs[Map[String, String], Nothing]

  def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing]

  def asKVPairListOption: ResponseAs[Option[List[KVPair]], Nothing]

  def asHealthCheckListUnsafe: ResponseAs[List[HealthCheck], Nothing]

  def asServiceEntryListUnsafe: ResponseAs[List[ServiceEntry], Nothing]

  def asNodeListUnsafe: ResponseAs[List[Node], Nothing]

  def asServiceInfoListUnsafe: ResponseAs[List[ServiceInfo], Nothing]

  def asNodeServiceListUnsafe: ResponseAs[NodeServiceList, Nothing]

  def asNodeServiceMap: ResponseAs[Option[NodeServiceMap], Nothing]
}
