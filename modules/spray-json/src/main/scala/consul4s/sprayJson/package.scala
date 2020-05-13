package consul4s

import consul4s.model.agent.AgentMember
import consul4s.model.catalog.{CatalogNode, CatalogNodeServiceList, CatalogService, Node}
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.sprayJson.model._
import sttp.client.ResponseAs
import sttp.client.sprayJson._

package object sprayJson extends Agent with Catalog with Common with Event with Health with KV with Transaction {
  implicit val jsonDecoder = new JsonDecoder {
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

    override def asCatalogServicesUnsafe: ResponseAs[List[CatalogService], Nothing] = asJsonAlwaysUnsafe[List[CatalogService]]

    override def asCatalogNodeOption: ResponseAs[Option[CatalogNode], Nothing] = asJsonAlways[CatalogNode].map(_.toOption)

    override def asCatalogNodeServiceListOption: ResponseAs[Option[CatalogNodeServiceList], Nothing] =
      asJsonAlways[CatalogNodeServiceList].map(_.toOption)

    override def asAgentMembersUnsafe: ResponseAs[List[AgentMember], Nothing] = asJsonAlwaysUnsafe[List[AgentMember]]
  }
}
