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

  implicit val scriptCheckEncoder: JsonEncoder[ScriptCheck] = DeriveJsonEncoder.gen[ScriptCheck]
  implicit val scriptCheckDecoder: JsonDecoder[ScriptCheck] = DeriveJsonDecoder.gen[ScriptCheck]

  implicit val httpCheckEncoder: JsonEncoder[HttpCheck] = DeriveJsonEncoder.gen[HttpCheck]
  implicit val httpCheckDecoder: JsonDecoder[HttpCheck] = DeriveJsonDecoder.gen[HttpCheck]

  implicit val tcpCheckEncoder: JsonEncoder[TCPCheck] = DeriveJsonEncoder.gen[TCPCheck]
  implicit val tcpCheckDecoder: JsonDecoder[TCPCheck] = DeriveJsonDecoder.gen[TCPCheck]

  implicit val ttlCheckEncoder: JsonEncoder[TTLCheck] = DeriveJsonEncoder.gen[TTLCheck]
  implicit val ttlCheckDecoder: JsonDecoder[TTLCheck] = DeriveJsonDecoder.gen[TTLCheck]

  implicit val dockerCheckEncoder: JsonEncoder[DockerCheck] = DeriveJsonEncoder.gen[DockerCheck]
  implicit val dockerCheckDecoder: JsonDecoder[DockerCheck] = DeriveJsonDecoder.gen[DockerCheck]

  implicit val grpcCheckEncoder: JsonEncoder[GRpcCheck] = DeriveJsonEncoder.gen[GRpcCheck]
  implicit val grpcCheckDecoder: JsonDecoder[GRpcCheck] = DeriveJsonDecoder.gen[GRpcCheck]

  implicit val checkEncoder: JsonEncoder[Check] = DeriveJsonEncoder.gen[Check]
  implicit val checkDecoder: JsonDecoder[Check] = DeriveJsonDecoder.gen[Check]

  implicit val serviceScriptCheckEncoder: JsonEncoder[ServiceScriptCheck] = DeriveJsonEncoder.gen[ServiceScriptCheck]
  implicit val serviceScriptCheckDecoder: JsonDecoder[ServiceScriptCheck] = DeriveJsonDecoder.gen[ServiceScriptCheck]

  implicit val serviceHttpCheckEncoder: JsonEncoder[ServiceHttpCheck] = DeriveJsonEncoder.gen[ServiceHttpCheck]
  implicit val serviceHttpCheckDecoder: JsonDecoder[ServiceHttpCheck] = DeriveJsonDecoder.gen[ServiceHttpCheck]

  implicit val serviceTCPCheckEncoder: JsonEncoder[ServiceTCPCheck] = DeriveJsonEncoder.gen[ServiceTCPCheck]
  implicit val serviceTCPCheckDecoder: JsonDecoder[ServiceTCPCheck] = DeriveJsonDecoder.gen[ServiceTCPCheck]

  implicit val serviceTTLCheckEncoder: JsonEncoder[ServiceTTLCheck] = DeriveJsonEncoder.gen[ServiceTTLCheck]
  implicit val serviceTTLCheckDecoder: JsonDecoder[ServiceTTLCheck] = DeriveJsonDecoder.gen[ServiceTTLCheck]

  implicit val serviceDockerCheckEncoder: JsonEncoder[ServiceDockerCheck] = DeriveJsonEncoder.gen[ServiceDockerCheck]
  implicit val serviceDockerCheckDecoder: JsonDecoder[ServiceDockerCheck] = DeriveJsonDecoder.gen[ServiceDockerCheck]

  implicit val serviceGRpcCheckEncoder: JsonEncoder[ServiceGRpcCheck] = DeriveJsonEncoder.gen[ServiceGRpcCheck]
  implicit val serviceGRpcCheckDecoder: JsonDecoder[ServiceGRpcCheck] = DeriveJsonDecoder.gen[ServiceGRpcCheck]

  implicit val serviceAliasCheckEncoder: JsonEncoder[ServiceAliasCheck] = DeriveJsonEncoder.gen[ServiceAliasCheck]
  implicit val serviceAliasCheckDecoder: JsonDecoder[ServiceAliasCheck] = DeriveJsonDecoder.gen[ServiceAliasCheck]

  implicit val serviceCheckEncoder: JsonEncoder[ServiceCheck] = DeriveJsonEncoder.gen[ServiceCheck]
  implicit val serviceCheckDecoder: JsonDecoder[ServiceCheck] = DeriveJsonDecoder.gen[ServiceCheck]

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
