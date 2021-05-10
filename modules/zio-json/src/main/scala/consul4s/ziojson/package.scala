package consul4s

import consul4s.model.agent.{AggregatedServiceStatus, Check, CheckUpdate, MemberInfo, NewService, Service, Token}
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog.{CatalogService, Node, NodeDeregistration, NodeRegistration, NodeServiceMap}
import consul4s.model.coordinate.{DatacenterCoordinate, NodeCoordinate}
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult
import consul4s.model.session.{NewSession, SessionId, SessionInfo}
import consul4s.model.transaction.{TxResults, TxTask}
import sttp.client3._
import sttp.client3.ziojson._
import consul4s.ziojson.model._
import zio.json.EncoderOps

import scala.util.control.NoStackTrace

package object ziojson extends PrimitiveTypes with SimpleTypes with ComplexTypes with DeepComplexTypes {

  final case class ParsingError(msg: String) extends Exception(msg) with NoStackTrace
  implicit val showParsingError: ShowError[ParsingError] = new ShowError[ParsingError] {
    override def show(t: ParsingError): String = t.msg
  }

  /**
   * Used only for /agent/health/service/name/:serviceName and /agent/health/service/id/:serviceId
   * 200	All healthchecks of every matching service instance are passing
   * 400	Bad parameter (missing service name of id)
   * 404	No such service id or name
   * 429	Some healthchecks are passing, at least one is warning
   * 503	At least one of the healthchecks is critical
   */
  private val allowedCodes = Set(200, 429, 503)

  private def asJsonMappedError[A](implicit
    decoder: zio.json.JsonDecoder[A]
  ): ResponseAs[Either[ConsulResponseError, A], Any] =
    asJson[A].mapLeft {
      case err @ HttpError(_, _)                 => err
      case DeserializationException(body, error) => DeserializationException[Exception](body, ParsingError(error))
    }

  private def asJsonOption404[A](implicit
    decoder: zio.json.JsonDecoder[Option[A]]
  ): ResponseAs[Either[ConsulResponseError, Option[A]], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.isSuccess) {
        deserializeJson[Option[A]].apply(str).fold(err => Left(DeserializationException(str, ParsingError(err))), res => Right(res))
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ConsulResponseError, Option[A]](HttpError(str, meta.code))
      }
    }

  private def asJsonOption404Extended[A](implicit
    decoder: zio.json.JsonDecoder[Option[A]]
  ): ResponseAs[Either[ConsulResponseError, Option[A]], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (allowedCodes.contains(meta.code.code)) {
        deserializeJson[Option[A]].apply(str).fold(err => Left(DeserializationException(str, ParsingError(err))), res => Right(res))
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ConsulResponseError, Option[A]](HttpError(str, meta.code))
      }
    }

  private def asJson200or409[A](implicit
    decoder: zio.json.JsonDecoder[A]
  ): ResponseAs[Either[ConsulResponseError, A], Any] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.code.code == 200 || meta.code.code == 409) {
        deserializeJson[A].apply(str).fold(err => Left(DeserializationException(str, ParsingError(err))), res => Right(res))
      } else {
        Left[ConsulResponseError, A](HttpError(str, meta.code))
      }
    }

  implicit val jsonDecoder: JsonDecoder = new JsonDecoder {
    override def asBoolean: ResponseAs[Either[ConsulResponseError, Boolean], Any] = asJsonMappedError[Boolean]

    override def asStringValue: ResponseAs[Either[ConsulResponseError, String], Any] = asJsonMappedError[String]

    override def asStringList: ResponseAs[Either[ConsulResponseError, List[String]], Any] = asJsonMappedError[List[String]]

    override def asStringListOption: ResponseAs[Either[ConsulResponseError, Option[List[String]]], Any] =
      asJsonOption404[List[String]]

    override def asMapSingleValue: ResponseAs[Either[ConsulResponseError, Map[String, String]], Any] =
      asJsonMappedError[Map[String, String]]

    override def asMapMultipleValues: ResponseAs[Either[ConsulResponseError, Map[String, List[String]]], Any] =
      asJsonMappedError[Map[String, List[String]]]

    override def asKVPairListOption: ResponseAs[Either[ConsulResponseError, Option[List[KVPair]]], Any] =
      asJsonOption404[List[KVPair]]

    override def asHealthCheckList: ResponseAs[Either[ConsulResponseError, List[HealthCheck]], Any] = asJsonMappedError[List[HealthCheck]]

    override def asServiceEntryList: ResponseAs[Either[ConsulResponseError, List[ServiceEntry]], Any] =
      asJsonMappedError[List[ServiceEntry]]

    override def asNodeList: ResponseAs[Either[ConsulResponseError, List[Node]], Any] = asJsonMappedError[List[Node]]

    override def asCatalogServiceList: ResponseAs[Either[ConsulResponseError, List[CatalogService]], Any] =
      asJsonMappedError[List[CatalogService]]

    override def asNodeServiceListInternal: ResponseAs[Either[ConsulResponseError, NodeServiceListInternal], Any] =
      asJsonMappedError[NodeServiceListInternal]

    override def asNodeServiceMap: ResponseAs[Either[ConsulResponseError, Option[NodeServiceMap]], Any] =
      asJsonOption404[NodeServiceMap]

    override def asUserEvent: ResponseAs[Either[ConsulResponseError, UserEvent], Any] = asJsonMappedError[UserEvent]

    override def asUserEventList: ResponseAs[Either[ConsulResponseError, List[UserEvent]], Any] = asJsonMappedError[List[UserEvent]]

    override def asSessionInfo: ResponseAs[Either[ConsulResponseError, SessionInfo], Any] = asJsonMappedError[SessionInfo]

    override def asSessionInfoList: ResponseAs[Either[ConsulResponseError, List[SessionInfo]], Any] = asJsonMappedError[List[SessionInfo]]

    override def asSessionId: ResponseAs[Either[ConsulResponseError, SessionId], Any] = asJsonMappedError[SessionId]

    override def asMemberInfoList: ResponseAs[Either[ConsulResponseError, List[MemberInfo]], Any] = asJsonMappedError[List[MemberInfo]]

    override def asHealthCheckMap: ResponseAs[Either[ConsulResponseError, Map[String, HealthCheck]], Any] =
      asJsonMappedError[Map[String, HealthCheck]]

    override def asServiceMap: ResponseAs[Either[ConsulResponseError, Map[String, Service]], Any] = asJsonMappedError[Map[String, Service]]

    override def asServiceOption: ResponseAs[Either[ConsulResponseError, Option[Service]], Any] = asJsonOption404[Service]

    override def asAggregatedServiceStatusOption: ResponseAs[Either[ConsulResponseError, Option[AggregatedServiceStatus]], Any] =
      asJsonOption404Extended[AggregatedServiceStatus]

    override def asAggregatedServiceStatusListOption: ResponseAs[Either[ConsulResponseError, Option[List[AggregatedServiceStatus]]], Any] =
      asJsonOption404Extended[List[AggregatedServiceStatus]]

    override def asDatacenterCoordinateList: ResponseAs[Either[ConsulResponseError, List[DatacenterCoordinate]], Any] =
      asJsonMappedError[List[DatacenterCoordinate]]

    override def asNodeCoordinateList: ResponseAs[Either[ConsulResponseError, List[NodeCoordinate]], Any] =
      asJsonMappedError[List[NodeCoordinate]]

    override def asNodeCoordinateListOption: ResponseAs[Either[ConsulResponseError, Option[List[NodeCoordinate]]], Any] =
      asJsonOption404[List[NodeCoordinate]]

    override def asQueryResultOption: ResponseAs[Either[ConsulResponseError, Option[QueryResult]], Any] =
      asJsonOption404[QueryResult]

    override def asTxResults: ResponseAs[Either[ConsulResponseError, TxResults], Any] = asJson200or409[TxResults]
  }

  implicit val jsonEncoder: JsonEncoder = new JsonEncoder {
    override def nodeRegistrationToJson(value: NodeRegistration): String = value.toJson

    override def nodeDeregistrationToJson(value: NodeDeregistration): String = value.toJson

    override def newSessionToJson(value: NewSession): String = value.toJson

    override def checkToJson(check: Check): String = check.toJson

    override def checkUpdateToJson(checkUpdate: CheckUpdate): String = checkUpdate.toJson

    override def newServiceToJson(service: NewService): String = service.toJson

    override def tokenAsJson(token: Token): String = token.toJson

    override def nodeCoordinateToJson(nodeCoordinate: NodeCoordinate): String = nodeCoordinate.toJson

    override def txTasksToJson(txTasks: List[TxTask]): String = txTasks.toJson
  }
}
