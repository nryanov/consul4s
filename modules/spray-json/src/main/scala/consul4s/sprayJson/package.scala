package consul4s

import consul4s.model.agent._
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult
import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import consul4s.model.transaction.{TxResults, TxTask}
import consul4s.sprayJson.model._
import sttp.client3.{DeserializationException, HttpError, ResponseAs, asStringAlways}
import sttp.client3.sprayJson._
import spray.json._

import scala.util.Try

package object sprayJson
    extends Agent
    with Catalog
    with Common
    with Event
    with Health
    with KV
    with Transaction
    with Session
    with Coordinate
    with Query {

  /**
   * Used only for /agent/health/service/name/:serviceName and /agent/health/service/id/:serviceId 200 All healthchecks of every matching
   * service instance are passing 400 Bad parameter (missing service name of id) 404 No such service id or name 429 Some healthchecks are
   * passing, at least one is warning 503 At least one of the healthchecks is critical
   */
  private val allowedCodes = Set(200, 429, 503)

  private def asJsonOption404[A](implicit
    reader: JsonReader[Option[A]]
  ): ResponseAs[Either[ConsulResponseError, Option[A]], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.isSuccess) {
        Try(deserializeJson[Option[A]].apply(str)).fold(err => Left(DeserializationException(str, new Exception(err))), res => Right(res))
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ConsulResponseError, Option[A]](HttpError(str, meta.code))
      }
    }

  private def asJsonOption404Extended[A](implicit
    reader: JsonReader[Option[A]]
  ): ResponseAs[Either[ConsulResponseError, Option[A]], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (allowedCodes.contains(meta.code.code)) {
        Try(deserializeJson[Option[A]].apply(str)).fold(err => Left(DeserializationException(str, new Exception(err))), res => Right(res))
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ConsulResponseError, Option[A]](HttpError(str, meta.code))
      }
    }

  private def asJson200or409[A: JsonReader]: ResponseAs[Either[ConsulResponseError, A], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.code.code == 200 || meta.code.code == 409) {
        Try(deserializeJson[A].apply(str)).fold(err => Left(DeserializationException(str, new Exception(err))), res => Right(res))
      } else {
        Left[ConsulResponseError, A](HttpError(str, meta.code))
      }
    }

  implicit val jsonDecoder: JsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Either[ConsulResponseError, Boolean], Any] = asJson[Boolean]

    override def asStringValue: ResponseAs[Either[ConsulResponseError, String], Any] = asJson[String]

    override def asStringList: ResponseAs[Either[ConsulResponseError, List[String]], Any] = asJson[List[String]]

    override def asStringListOption: ResponseAs[Either[ConsulResponseError, Option[List[String]]], Any] =
      asJsonOption404[List[String]]

    override def asMapSingleValue: ResponseAs[Either[ConsulResponseError, Map[String, String]], Any] =
      asJson[Map[String, String]]

    override def asMapMultipleValues: ResponseAs[Either[ConsulResponseError, Map[String, List[String]]], Any] =
      asJson[Map[String, List[String]]]

    override def asKVPairListOption: ResponseAs[Either[ConsulResponseError, Option[List[KVPair]]], Any] =
      asJsonOption404[List[KVPair]]

    override def asHealthCheckList: ResponseAs[Either[ConsulResponseError, List[HealthCheck]], Any] =
      asJson[List[HealthCheck]]

    override def asServiceEntryList: ResponseAs[Either[ConsulResponseError, List[ServiceEntry]], Any] =
      asJson[List[ServiceEntry]]

    override def asNodeList: ResponseAs[Either[ConsulResponseError, List[Node]], Any] = asJson[List[Node]]

    override def asCatalogServiceList: ResponseAs[Either[ConsulResponseError, List[CatalogService]], Any] =
      asJson[List[CatalogService]]

    override def asNodeServiceListInternal: ResponseAs[Either[ConsulResponseError, NodeServiceListInternal], Any] =
      asJson[NodeServiceListInternal]

    override def asNodeServiceMap: ResponseAs[Either[ConsulResponseError, Option[NodeServiceMap]], Any] =
      asJsonOption404[NodeServiceMap]

    override def asUserEvent: ResponseAs[Either[ConsulResponseError, UserEvent], Any] = asJson[UserEvent]

    override def asUserEventList: ResponseAs[Either[ConsulResponseError, List[UserEvent]], Any] = asJson[List[UserEvent]]

    override def asSessionInfo: ResponseAs[Either[ConsulResponseError, SessionInfo], Any] = asJson[SessionInfo]

    override def asSessionInfoList: ResponseAs[Either[ConsulResponseError, List[SessionInfo]], Any] =
      asJson[List[SessionInfo]]

    override def asSessionId: ResponseAs[Either[ConsulResponseError, SessionId], Any] = asJson[SessionId]

    override def asMemberInfoList: ResponseAs[Either[ConsulResponseError, List[MemberInfo]], Any] = asJson[List[MemberInfo]]

    override def asHealthCheckMap: ResponseAs[Either[ConsulResponseError, Map[String, HealthCheck]], Any] =
      asJson[Map[String, HealthCheck]]

    override def asServiceMap: ResponseAs[Either[ConsulResponseError, Map[String, Service]], Any] =
      asJson[Map[String, Service]]

    override def asServiceOption: ResponseAs[Either[ConsulResponseError, Option[Service]], Any] = asJsonOption404[Service]

    override def asAggregatedServiceStatusOption: ResponseAs[Either[ConsulResponseError, Option[AggregatedServiceStatus]], Any] =
      asJsonOption404Extended[AggregatedServiceStatus]

    override def asAggregatedServiceStatusListOption: ResponseAs[Either[ConsulResponseError, Option[List[AggregatedServiceStatus]]], Any] =
      asJsonOption404Extended[List[AggregatedServiceStatus]]

    override def asDatacenterCoordinateList: ResponseAs[Either[ConsulResponseError, List[DatacenterCoordinate]], Any] =
      asJson[List[DatacenterCoordinate]]

    override def asNodeCoordinateList: ResponseAs[Either[ConsulResponseError, List[NodeCoordinate]], Any] =
      asJson[List[NodeCoordinate]]

    override def asNodeCoordinateListOption: ResponseAs[Either[ConsulResponseError, Option[List[NodeCoordinate]]], Any] =
      asJsonOption404[List[NodeCoordinate]]

    override def asQueryResultOption: ResponseAs[Either[ConsulResponseError, Option[QueryResult]], Any] =
      asJsonOption404[QueryResult]

    override def asTxResults: ResponseAs[Either[ConsulResponseError, TxResults], Any] = asJson200or409[TxResults]
  }

  implicit val jsonEncoder: JsonEncoder = new JsonEncoder {
    override def nodeRegistrationToJson(value: NodeRegistration): String = value.toJson.compactPrint

    override def nodeDeregistrationToJson(value: NodeDeregistration): String = value.toJson.compactPrint

    override def newSessionToJson(value: NewSession): String = value.toJson.compactPrint

    override def checkToJson(check: Check): String = check.toJson.compactPrint

    override def checkUpdateToJson(checkUpdate: CheckUpdate): String = checkUpdate.toJson.compactPrint

    override def newServiceToJson(service: NewService): String = service.toJson.compactPrint

    override def tokenAsJson(token: Token): String = token.toJson.compactPrint

    override def nodeCoordinateToJson(nodeCoordinate: NodeCoordinate): String = nodeCoordinate.toJson.compactPrint

    override def txTasksToJson(txTasks: List[TxTask]): String = txTasks.toJson.compactPrint
  }
}
