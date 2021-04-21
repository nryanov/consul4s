package consul4s.ziojson.model

import consul4s.model.query.QueryResult
import consul4s.model.query.QueryResult.DNS
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Query { this: Health =>
  implicit val dnsEncoder: JsonEncoder[DNS] = DeriveJsonEncoder.gen[DNS]
  implicit val queryResultEncoder: JsonEncoder[QueryResult] = DeriveJsonEncoder.gen[QueryResult]

  implicit val dnsCodec: JsonDecoder[DNS] = DeriveJsonDecoder.gen[DNS]
  implicit val queryResultCodec: JsonDecoder[QueryResult] = DeriveJsonDecoder.gen[QueryResult]
}
