package consul4s

import consul4s.circe.model._
import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.event.UserEvent
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.model.session.{SessionId, SessionInfo}
import sttp.client._
import sttp.client.circe._
import io.circe._
import io.circe.syntax._

package object circe extends Agent with Catalog with Common with Event with Health with KV with Transaction with Session {
  private def asJsonOption404[A: Decoder: IsOption]: ResponseAs[Either[ResponseError[Exception], Option[A]], Nothing] =
    asStringAlways.mapWithMetadata { (str, meta) =>
      if (meta.isSuccess) {
        str.asJson.as[Option[A]].fold(err => Left(DeserializationError(str, err)), res => Right(res))
      } else if (meta.code.code == 404) {
        Right(None)
      } else {
        Left[ResponseError[Exception], Option[A]](HttpError(str))
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

    override def asServiceInfoList: ResponseAs[Either[ResponseError[Exception], List[ServiceInfo]], Nothing] = asJson[List[ServiceInfo]]

    override def asNodeServiceList: ResponseAs[Either[ResponseError[Exception], NodeServiceList], Nothing] = asJson[NodeServiceList]

    override def asNodeServiceMap: ResponseAs[Either[ResponseError[Exception], Option[NodeServiceMap]], Nothing] =
      asJsonOption404[NodeServiceMap]

    override def asUserEvent: ResponseAs[Either[ResponseError[Exception], UserEvent], Nothing] = asJson[UserEvent]

    override def asUserEventList: ResponseAs[Either[ResponseError[Exception], List[UserEvent]], Nothing] = asJson[List[UserEvent]]

    override def asSessionInfo: ResponseAs[Either[ResponseError[Exception], SessionInfo], Nothing] = asJson[SessionInfo]

    override def asSessionInfoList: ResponseAs[Either[ResponseError[Exception], List[SessionInfo]], Nothing] = asJson[List[SessionInfo]]

    override def asSessionId: ResponseAs[Either[ResponseError[Exception], SessionId], Nothing] = asJson[SessionId]
  }

  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  implicit val jsonEncoder: JsonEncoder = new JsonEncoder {
    override def entityRegistrationToJson(value: EntityRegistration): String = printer.print(value.asJson)

    override def entityDeregistrationToJson(value: EntityDeregistration): String = printer.print(value.asJson)

    override def sessionToJson(value: SessionInfo): String = printer.print(value.asJson)
  }
}
