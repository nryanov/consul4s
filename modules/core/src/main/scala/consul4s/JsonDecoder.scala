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

  def asKVPairsOption: ResponseAs[Option[List[KVPair]], Nothing]

  def asHealthChecksUnsafe: ResponseAs[List[HealthCheck], Nothing]

  def asServiceEntriesUnsafe: ResponseAs[List[ServiceEntry], Nothing]

  def asNodesUnsafe: ResponseAs[List[Node], Nothing]
}
