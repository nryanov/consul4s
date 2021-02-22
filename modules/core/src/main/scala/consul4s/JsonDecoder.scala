package consul4s

import consul4s.model.agent._
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult
import consul4s.model.session._
import consul4s.model.transaction.TxResults
import sttp.client3._

trait JsonDecoder {
  def asBoolean: ResponseAs[Either[ConsulResponseError, Boolean], Any]

  def asStringValue: ResponseAs[Either[ConsulResponseError, String], Any]

  def asStringList: ResponseAs[Either[ConsulResponseError, List[String]], Any]

  def asStringListOption: ResponseAs[Either[ConsulResponseError, Option[List[String]]], Any]

  def asMapSingleValue: ResponseAs[Either[ConsulResponseError, Map[String, String]], Any]

  def asMapMultipleValues: ResponseAs[Either[ConsulResponseError, Map[String, List[String]]], Any]

  def asKVPairListOption: ResponseAs[Either[ConsulResponseError, Option[List[KVPair]]], Any]

  def asHealthCheckList: ResponseAs[Either[ConsulResponseError, List[HealthCheck]], Any]

  def asServiceEntryList: ResponseAs[Either[ConsulResponseError, List[ServiceEntry]], Any]

  def asNodeList: ResponseAs[Either[ConsulResponseError, List[Node]], Any]

  def asCatalogServiceList: ResponseAs[Either[ConsulResponseError, List[CatalogService]], Any]

  def asNodeServiceListInternal: ResponseAs[Either[ConsulResponseError, NodeServiceListInternal], Any]

  def asNodeServiceMap: ResponseAs[Either[ConsulResponseError, Option[NodeServiceMap]], Any]

  def asUserEvent: ResponseAs[Either[ConsulResponseError, UserEvent], Any]

  def asUserEventList: ResponseAs[Either[ConsulResponseError, List[UserEvent]], Any]

  def asSessionInfo: ResponseAs[Either[ConsulResponseError, SessionInfo], Any]

  def asSessionInfoList: ResponseAs[Either[ConsulResponseError, List[SessionInfo]], Any]

  def asSessionId: ResponseAs[Either[ConsulResponseError, SessionId], Any]

  def asMemberInfoList: ResponseAs[Either[ConsulResponseError, List[MemberInfo]], Any]

  def asHealthCheckMap: ResponseAs[Either[ConsulResponseError, Map[String, HealthCheck]], Any]

  def asServiceMap: ResponseAs[Either[ConsulResponseError, Map[String, Service]], Any]

  def asServiceOption: ResponseAs[Either[ConsulResponseError, Option[Service]], Any]

  def asAggregatedServiceStatusOption: ResponseAs[Either[ConsulResponseError, Option[AggregatedServiceStatus]], Any]

  def asAggregatedServiceStatusListOption: ResponseAs[Either[ConsulResponseError, Option[List[AggregatedServiceStatus]]], Any]

  def asDatacenterCoordinateList: ResponseAs[Either[ConsulResponseError, List[DatacenterCoordinate]], Any]

  def asNodeCoordinateList: ResponseAs[Either[ConsulResponseError, List[NodeCoordinate]], Any]

  def asNodeCoordinateListOption: ResponseAs[Either[ConsulResponseError, Option[List[NodeCoordinate]]], Any]

  def asQueryResultOption: ResponseAs[Either[ConsulResponseError, Option[QueryResult]], Any]

  def asTxResults: ResponseAs[Either[ConsulResponseError, TxResults], Any]
}
