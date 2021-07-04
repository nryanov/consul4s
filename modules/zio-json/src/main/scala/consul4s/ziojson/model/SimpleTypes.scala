package consul4s.ziojson.model

import consul4s.model.agent._
import consul4s.model.catalog.{CatalogService, NewCatalogService}
import consul4s.model.coordinate.NodeCoordinate
import consul4s.model.health.{HealthCheck, NewHealthCheck}
import consul4s.model.session.{NewSession, SessionInfo}
import consul4s.model.transaction.TxTask.{KVTask, NodeTask}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait SimpleTypes extends PrimitiveTypes {
  implicit val sessionInfoEncoder: JsonEncoder[SessionInfo] = DeriveJsonEncoder.gen[SessionInfo]
  implicit val sessionInfoDecoder: JsonDecoder[SessionInfo] = DeriveJsonDecoder.gen[SessionInfo]

  implicit val newSessionInfoEncoder: JsonEncoder[NewSession] = DeriveJsonEncoder.gen[NewSession]
  implicit val newSessionInfoDecoder: JsonDecoder[NewSession] = DeriveJsonDecoder.gen[NewSession]

  implicit val kvTaskEncoder: JsonEncoder[KVTask] = DeriveJsonEncoder.gen[KVTask]
  implicit val kvTaskDecoder: JsonDecoder[KVTask] = DeriveJsonDecoder.gen[KVTask]

  implicit val checkUpdateEncoder: JsonEncoder[CheckUpdate] = DeriveJsonEncoder.gen[CheckUpdate]
  implicit val checkUpdateDecoder: JsonDecoder[CheckUpdate] = DeriveJsonDecoder.gen[CheckUpdate]

  implicit val checkEncoder: JsonEncoder[Check] = DeriveJsonEncoder.gen[CheckRepr].contramap[Check](v => CheckRepr.fromCheck(v))
  implicit val checkDecoder: JsonDecoder[Check] = DeriveJsonDecoder.gen[CheckRepr].map(v => CheckRepr.toCheck(v)).<>()

  implicit val serviceCheckEncoder: JsonEncoder[ServiceCheck] =
    DeriveJsonEncoder.gen[ServiceCheckRepr].contramap[ServiceCheck](v => ServiceCheckRepr.fromServiceCheck(v))
  implicit val serviceCheckDecoder: JsonDecoder[ServiceCheck] =
    DeriveJsonDecoder.gen[ServiceCheckRepr].map(v => ServiceCheckRepr.toServiceCheck(v))

  implicit val nodeCoordinateEncoder: JsonEncoder[NodeCoordinate] = DeriveJsonEncoder.gen[NodeCoordinate]
  implicit val nodeCoordinateDecoder: JsonDecoder[NodeCoordinate] = DeriveJsonDecoder.gen[NodeCoordinate]

  implicit val newCatalogServiceEncoder: JsonEncoder[NewCatalogService] = DeriveJsonEncoder.gen[NewCatalogService]
  implicit val newCatalogServiceDecoder: JsonDecoder[NewCatalogService] = DeriveJsonDecoder.gen[NewCatalogService]

  implicit val serviceEncoder: JsonEncoder[Service] = DeriveJsonEncoder.gen[Service]
  implicit val serviceDecoder: JsonDecoder[Service] = DeriveJsonDecoder.gen[Service]

  implicit val catalogServiceEncoder: JsonEncoder[CatalogService] = DeriveJsonEncoder.gen[CatalogService]
  implicit val catalogServiceDecoder: JsonDecoder[CatalogService] = DeriveJsonDecoder.gen[CatalogService]

  implicit val newHealthCheckEncoder: JsonEncoder[NewHealthCheck] = DeriveJsonEncoder.gen[NewHealthCheck]
  implicit val newHealthCheckDecoder: JsonDecoder[NewHealthCheck] = DeriveJsonDecoder.gen[NewHealthCheck]

  implicit val healthCheckEncoder: JsonEncoder[HealthCheck] = DeriveJsonEncoder.gen[HealthCheck]
  implicit val healthCheckDecoder: JsonDecoder[HealthCheck] = DeriveJsonDecoder.gen[HealthCheck]

  implicit val nodeTaskEncoder: JsonEncoder[NodeTask] = DeriveJsonEncoder.gen[NodeTask]
  implicit val nodeTaskDecoder: JsonDecoder[NodeTask] = DeriveJsonDecoder.gen[NodeTask]
}
