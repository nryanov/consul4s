package consul4s

import consul4s.circe.model._
import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import sttp.client.ResponseAs
import sttp.client.circe._
import io.circe._
import io.circe.syntax._

package object circe extends Agent with Catalog with Common with Event with Health with KV with Transaction {
  implicit val jsonDecoder: JsonDecoder = new JsonDecoder {
    override def asBooleanUnsafe: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asStringUnsafe: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringListUnsafe: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]

    override def asStringListOption: ResponseAs[Option[List[String]], Nothing] = asJsonAlways[List[String]].map(_.toOption)

    override def asMapUnsafe: ResponseAs[Map[String, String], Nothing] = asJsonAlwaysUnsafe[Map[String, String]]

    override def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing] = asJsonAlwaysUnsafe[Map[String, List[String]]]

    override def asKVPairsOption: ResponseAs[Option[List[KVPair]], Nothing] = asJsonAlways[List[KVPair]].map(_.toOption)

    override def asHealthChecksUnsafe: ResponseAs[List[HealthCheck], Nothing] = asJsonAlwaysUnsafe[List[HealthCheck]]

    override def asServiceEntriesUnsafe: ResponseAs[List[ServiceEntry], Nothing] = asJsonAlwaysUnsafe[List[ServiceEntry]]

    override def asNodesUnsafe: ResponseAs[List[Node], Nothing] = asJsonAlwaysUnsafe[List[Node]]
  }

  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  implicit val jsonEncoder: JsonEncoder = new JsonEncoder {
    override def entityRegistrationToJson(value: EntityRegistration): String = printer.print(value.asJson)

    override def entityDeregistrationToJson(value: EntityDeregistration): String = printer.print(value.asJson)
  }
}
