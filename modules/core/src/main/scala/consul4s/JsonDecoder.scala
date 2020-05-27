package consul4s

import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult
import consul4s.model.session._
import consul4s.model.transaction.TxResults
import sttp.client._

trait JsonDecoder {
  def asBoolean: ResponseAs[Either[ResponseError[Exception], Boolean], Nothing]

  def asStringValue: ResponseAs[Either[ResponseError[Exception], String], Nothing]

  def asStringList: ResponseAs[Either[ResponseError[Exception], List[String]], Nothing]

  def asStringListOption: ResponseAs[Either[ResponseError[Exception], Option[List[String]]], Nothing]

  def asMapSingleValue: ResponseAs[Either[ResponseError[Exception], Map[String, String]], Nothing]

  def asMapMultipleValues: ResponseAs[Either[ResponseError[Exception], Map[String, List[String]]], Nothing]

  def asKVPairListOption: ResponseAs[Either[ResponseError[Exception], Option[List[KVPair]]], Nothing]

  def asHealthCheckList: ResponseAs[Either[ResponseError[Exception], List[HealthCheck]], Nothing]

  def asServiceEntryList: ResponseAs[Either[ResponseError[Exception], List[ServiceEntry]], Nothing]

  def asNodeList: ResponseAs[Either[ResponseError[Exception], List[Node]], Nothing]

  def asCatalogServiceList: ResponseAs[Either[ResponseError[Exception], List[CatalogService]], Nothing]

  def asNodeServiceList: ResponseAs[Either[ResponseError[Exception], NodeServiceList], Nothing]

  def asNodeServiceMap: ResponseAs[Either[ResponseError[Exception], Option[NodeServiceMap]], Nothing]

  def asUserEvent: ResponseAs[Either[ResponseError[Exception], UserEvent], Nothing]

  def asUserEventList: ResponseAs[Either[ResponseError[Exception], List[UserEvent]], Nothing]

  def asSessionInfo: ResponseAs[Either[ResponseError[Exception], SessionInfo], Nothing]

  def asSessionInfoList: ResponseAs[Either[ResponseError[Exception], List[SessionInfo]], Nothing]

  def asSessionId: ResponseAs[Either[ResponseError[Exception], SessionId], Nothing]

  def asMemberInfoList: ResponseAs[Either[ResponseError[Exception], List[MemberInfo]], Nothing]

  def asHealthCheckMap: ResponseAs[Either[ResponseError[Exception], Map[String, HealthCheck]], Nothing]

  def asServiceMap: ResponseAs[Either[ResponseError[Exception], Map[String, Service]], Nothing]

  def asServiceOption: ResponseAs[Either[ResponseError[Exception], Option[Service]], Nothing]

  def asAggregatedServiceStatusOption: ResponseAs[Either[ResponseError[Exception], Option[AggregatedServiceStatus]], Nothing]

  def asAggregatedServiceStatusListOption: ResponseAs[Either[ResponseError[Exception], Option[List[AggregatedServiceStatus]]], Nothing]

  def asDatacenterCoordinateList: ResponseAs[Either[ResponseError[Exception], List[DatacenterCoordinate]], Nothing]

  def asNodeCoordinateList: ResponseAs[Either[ResponseError[Exception], List[NodeCoordinate]], Nothing]

  def asNodeCoordinateListOption: ResponseAs[Either[ResponseError[Exception], Option[List[NodeCoordinate]]], Nothing]

  def asQueryResultOption: ResponseAs[Either[ResponseError[Exception], Option[QueryResult]], Nothing]

  def asTxResults: ResponseAs[Either[ResponseError[Exception], TxResults], Nothing]
}
