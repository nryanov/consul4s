package consul4s.circe.model

import consul4s.model.health.ServiceEntry
import consul4s.model.query.QueryResult
import consul4s.model.query.QueryResult._
import io.circe.Decoder.Result
import io.circe._

trait Query { this: Health =>
  implicit val dnsDecoder: Decoder[DNS] = new Decoder[DNS] {
    override def apply(c: HCursor): Result[DNS] = for {
      ttl <- c.downField("TTL").as[String]
    } yield DNS(ttl)
  }

  implicit val queryResultDecoder: Decoder[QueryResult] = new Decoder[QueryResult] {
    override def apply(c: HCursor): Result[QueryResult] = for {
      service <- c.downField("Service").as[String]
      datacenter <- c.downField("Datacenter").as[String]
      dns <- c.downField("DNS").as[DNS]
      nodes <- c.downField("Nodes").as[List[ServiceEntry]]
    } yield QueryResult(service, datacenter, dns, nodes)
  }

}
