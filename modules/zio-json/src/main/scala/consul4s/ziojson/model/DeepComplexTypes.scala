package consul4s.ziojson.model

import consul4s.model.query.QueryResult
import consul4s.model.transaction.{TxResults, TxTask}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait DeepComplexTypes extends ComplexTypes {
  implicit val queryResultEncoder: JsonEncoder[QueryResult] = DeriveJsonEncoder.gen[QueryResult]
  implicit val queryResultCodec: JsonDecoder[QueryResult] = DeriveJsonDecoder.gen[QueryResult]

  implicit val txTaskEncoder: JsonEncoder[TxTask] = DeriveJsonEncoder.gen[TxTask]
  implicit val txTaskDecoder: JsonDecoder[TxTask] = DeriveJsonDecoder.gen[TxTask]

  implicit val txResultsEncoder: JsonEncoder[TxResults] = DeriveJsonEncoder.gen[TxResults]
  implicit val txResultsDecoder: JsonDecoder[TxResults] = DeriveJsonDecoder.gen[TxResults]
}
