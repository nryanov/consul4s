package consul4s.ziojson.model

import consul4s.model.query.QueryResult
import consul4s.model.query.QueryResult.DNS
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Query { this: Health =>
  implicit val dnsCodec: JsonCodec[DNS] = DeriveJsonCodec.gen[DNS]
  implicit val queryResultCodec: JsonCodec[QueryResult] = DeriveJsonCodec.gen[QueryResult]
}
