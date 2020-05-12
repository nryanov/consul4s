package consul4s

import consul4s.circe.model._
import consul4s.model.agent.AgentMember
import consul4s.model.catalog.{CatalogNode, CatalogNodeServiceList, CatalogService, Node}
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import sttp.client.ResponseAs
import sttp.client.circe._
import io.circe._

package object circe extends Agent with Catalog with Common with Event with Health with KV with Transaction {
  implicit val jsonDecoder = new JsonDecoder {
    override def asBooleanUnsafe: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asStringUnsafe: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringListUnsafe: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]

    override def asStringListOption: ResponseAs[Option[List[String]], Nothing] = asJsonAlwaysUnsafe[Option[List[String]]]

    override def asMapUnsafe: ResponseAs[Map[String, String], Nothing] = asJsonAlwaysUnsafe[Map[String, String]]

    override def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing] = asJsonAlwaysUnsafe[Map[String, List[String]]]

    override def asKVPairsOption: ResponseAs[Option[List[KVPair]], Nothing] = asJsonAlwaysUnsafe[Option[List[KVPair]]]

    override def asHealthChecksUnsafe: ResponseAs[List[HealthCheck], Nothing] = asJsonAlwaysUnsafe[List[HealthCheck]]

    override def asServiceEntriesUnsafe: ResponseAs[List[ServiceEntry], Nothing] = ???

    override def asNodesUnsafe: ResponseAs[List[Node], Nothing] = asJsonAlwaysUnsafe[List[Node]]

    override def asCatalogServicesUnsafe: ResponseAs[List[CatalogService], Nothing] = asJsonAlwaysUnsafe[List[CatalogService]]

    override def asCatalogNodeOption: ResponseAs[Option[CatalogNode], Nothing] = asJsonAlwaysUnsafe[Option[CatalogNode]]

    override def asCatalogNodeServiceListOption: ResponseAs[Option[CatalogNodeServiceList], Nothing] =
      asJsonAlwaysUnsafe[Option[CatalogNodeServiceList]]

    override def asAgentMembersUnsafe: ResponseAs[List[AgentMember], Nothing] = asJsonAlwaysUnsafe[List[AgentMember]]
  }
}
