package consul4s

import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult
import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import consul4s.model.transaction.{TxResults, TxTask}
import consul4s.sprayJson.model._
import sttp.client.{DeserializationError, HttpError, ResponseAs, ResponseError, asStringAlways}
import sttp.client.sprayJson._
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
   * Used only for /agent/health/service/name/:serviceName and /agent/health/service/id/:serviceId
   * 200	All healthchecks of every matching service instance are passing
   * 400	Bad parameter (missing service name of id)
   * 404	No such service id or name
   * 429	Some healthchecks are passing, at least one is warning
   * 503	At least one of the healthchecks is critical
   */
  private val allowedCodes = Set(200, 429, 503)

  private def asJsonOption404[A: JsonReader]: ResponseAs[Either[ResponseError[Exception], Option[A]], Nothing] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.isSuccess) {
        if (str != null && !str.isBlank) {
          Try(deserializeJson[A].apply(str)).fold(err => Left(DeserializationError(str, new Exception(err))), res => Right(Some(res)))
        } else {
          Right(None)
        }
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ResponseError[Exception], Option[A]](HttpError(str))
      }
    }

  private def asJsonOption404Extended[A: JsonReader]: ResponseAs[Either[ResponseError[Exception], Option[A]], Nothing] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (allowedCodes.contains(meta.code.code)) {
        if (str != null && !str.isBlank) {
          Try(deserializeJson[A].apply(str)).fold(err => Left(DeserializationError(str, new Exception(err))), res => Right(Some(res)))
        } else {
          Right(None)
        }
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ResponseError[Exception], Option[A]](HttpError(str))
      }
    }

  private def asJson200or409[A: JsonReader]: ResponseAs[Either[ResponseError[Exception], A], Nothing] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.code.code == 200 || meta.code.code == 409) {
        Try(deserializeJson[A].apply(str)).fold(err => Left(DeserializationError(str, new Exception(err))), res => Right(res))
      } else {
        Left[ResponseError[Exception], A](HttpError(str))
      }
    }

  implicit val jsonDecoder: JsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Either[ResponseError[Exception], Boolean], Nothing] = asJson[Boolean]

    override def asStringValue: ResponseAs[Either[ResponseError[Exception], String], Nothing] = asJson[String]

    override def asStringList: ResponseAs[Either[ResponseError[Exception], List[String]], Nothing] = asJson[List[String]]

    override def asStringListOption: ResponseAs[Either[ResponseError[Exception], Option[List[String]]], Nothing] =
      asJsonOption404[List[String]]

    override def asMapSingleValue: ResponseAs[Either[ResponseError[Exception], Map[String, String]], Nothing] = asJson[Map[String, String]]

    override def asMapMultipleValues: ResponseAs[Either[ResponseError[Exception], Map[String, List[String]]], Nothing] =
      asJson[Map[String, List[String]]]

    override def asKVPairListOption: ResponseAs[Either[ResponseError[Exception], Option[List[KVPair]]], Nothing] =
      asJsonOption404[List[KVPair]]

    override def asHealthCheckList: ResponseAs[Either[ResponseError[Exception], List[HealthCheck]], Nothing] = asJson[List[HealthCheck]]

    override def asServiceEntryList: ResponseAs[Either[ResponseError[Exception], List[ServiceEntry]], Nothing] = asJson[List[ServiceEntry]]

    override def asNodeList: ResponseAs[Either[ResponseError[Exception], List[Node]], Nothing] = asJson[List[Node]]

    override def asCatalogServiceList: ResponseAs[Either[ResponseError[Exception], List[CatalogService]], Nothing] =
      asJson[List[CatalogService]]

    override def asNodeServiceList: ResponseAs[Either[ResponseError[Exception], NodeServiceList], Nothing] = asJson[NodeServiceList]

    override def asNodeServiceMap: ResponseAs[Either[ResponseError[Exception], Option[NodeServiceMap]], Nothing] =
      asJsonOption404[NodeServiceMap]

    override def asUserEvent: ResponseAs[Either[ResponseError[Exception], UserEvent], Nothing] = asJson[UserEvent]

    override def asUserEventList: ResponseAs[Either[ResponseError[Exception], List[UserEvent]], Nothing] = asJson[List[UserEvent]]

    override def asSessionInfo: ResponseAs[Either[ResponseError[Exception], SessionInfo], Nothing] = asJson[SessionInfo]

    override def asSessionInfoList: ResponseAs[Either[ResponseError[Exception], List[SessionInfo]], Nothing] = asJson[List[SessionInfo]]

    override def asSessionId: ResponseAs[Either[ResponseError[Exception], SessionId], Nothing] = asJson[SessionId]

    override def asMemberInfoList: ResponseAs[Either[ResponseError[Exception], List[MemberInfo]], Nothing] = asJson[List[MemberInfo]]

    override def asHealthCheckMap: ResponseAs[Either[ResponseError[Exception], Map[String, HealthCheck]], Nothing] =
      asJson[Map[String, HealthCheck]]

    override def asServiceMap: ResponseAs[Either[ResponseError[Exception], Map[String, Service]], Nothing] = asJson[Map[String, Service]]

    override def asServiceOption: ResponseAs[Either[ResponseError[Exception], Option[Service]], Nothing] = asJsonOption404[Service]

    override def asAggregatedServiceStatusOption: ResponseAs[Either[ResponseError[Exception], Option[AggregatedServiceStatus]], Nothing] =
      asJsonOption404Extended[AggregatedServiceStatus]

    override def asAggregatedServiceStatusListOption
      : ResponseAs[Either[ResponseError[Exception], Option[List[AggregatedServiceStatus]]], Nothing] =
      asJsonOption404Extended[List[AggregatedServiceStatus]]

    override def asDatacenterCoordinateList: ResponseAs[Either[ResponseError[Exception], List[DatacenterCoordinate]], Nothing] =
      asJson[List[DatacenterCoordinate]]

    override def asNodeCoordinateList: ResponseAs[Either[ResponseError[Exception], List[NodeCoordinate]], Nothing] =
      asJson[List[NodeCoordinate]]

    override def asNodeCoordinateListOption: ResponseAs[Either[ResponseError[Exception], Option[List[NodeCoordinate]]], Nothing] =
      asJsonOption404[List[NodeCoordinate]]

    override def asQueryResultOption: ResponseAs[Either[ResponseError[Exception], Option[QueryResult]], Nothing] =
      asJsonOption404[QueryResult]

    override def asTxResults: ResponseAs[Either[ResponseError[Exception], TxResults], Nothing] = asJson200or409[TxResults]
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
