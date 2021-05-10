package consul4s.ziojson.model

import consul4s.model.agent.{AggregatedServiceStatus, NewService}
import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog.{NodeRegistration, NodeServiceMap}
import consul4s.model.coordinate.DatacenterCoordinate
import consul4s.model.health.ServiceEntry
import consul4s.model.transaction.TxResults.TxResult
import consul4s.model.transaction.TxTask.{CheckTask, ServiceTask}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait ComplexTypes extends SimpleTypes {
  implicit val nodeServiceListInternalEncoder: JsonEncoder[NodeServiceListInternal] = DeriveJsonEncoder.gen[NodeServiceListInternal]
  implicit val nodeServiceListInternalDecoder: JsonDecoder[NodeServiceListInternal] = DeriveJsonDecoder.gen[NodeServiceListInternal]

  implicit val nodeServiceMapEncoder: JsonEncoder[NodeServiceMap] = DeriveJsonEncoder.gen[NodeServiceMap]
  implicit val nodeServiceMapDecoder: JsonDecoder[NodeServiceMap] = DeriveJsonDecoder.gen[NodeServiceMap]

  implicit val datacenterCoordinateEncoder: JsonEncoder[DatacenterCoordinate] = DeriveJsonEncoder.gen[DatacenterCoordinate]
  implicit val datacenterCoordinateDecoder: JsonDecoder[DatacenterCoordinate] = DeriveJsonDecoder.gen[DatacenterCoordinate]

  implicit val newServiceEncoder: JsonEncoder[NewService] = DeriveJsonEncoder.gen[NewService]
  implicit val newServiceDecoder: JsonDecoder[NewService] = DeriveJsonDecoder.gen[NewService]

  implicit val aggregatedServiceStatusEncoder: JsonEncoder[AggregatedServiceStatus] = DeriveJsonEncoder.gen[AggregatedServiceStatus]
  implicit val aggregatedServiceStatusDecoder: JsonDecoder[AggregatedServiceStatus] = DeriveJsonDecoder.gen[AggregatedServiceStatus]

  implicit val nodeRegistrationEncoder: JsonEncoder[NodeRegistration] = DeriveJsonEncoder.gen[NodeRegistration]
  implicit val nodeRegistrationDecoder: JsonDecoder[NodeRegistration] = DeriveJsonDecoder.gen[NodeRegistration]

  implicit val serviceEntryEncoder: JsonEncoder[ServiceEntry] = DeriveJsonEncoder.gen[ServiceEntry]
  implicit val serviceEntryDecoder: JsonDecoder[ServiceEntry] = DeriveJsonDecoder.gen[ServiceEntry]

  implicit val txResultEncoder: JsonEncoder[TxResult] = DeriveJsonEncoder.gen[TxResult]
  implicit val txResultDecoder: JsonDecoder[TxResult] = DeriveJsonDecoder.gen[TxResult]

  implicit val checkTaskEncoder: JsonEncoder[CheckTask] = DeriveJsonEncoder.gen[CheckTask]
  implicit val checkTaskDecoder: JsonDecoder[CheckTask] = DeriveJsonDecoder.gen[CheckTask]

  implicit val serviceTaskEncoder: JsonEncoder[ServiceTask] = DeriveJsonEncoder.gen[ServiceTask]
  implicit val serviceTaskDecoder: JsonDecoder[ServiceTask] = DeriveJsonDecoder.gen[ServiceTask]
}
