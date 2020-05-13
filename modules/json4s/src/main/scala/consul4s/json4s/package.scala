package consul4s

import consul4s.json4s.model._
import consul4s.model.agent.AgentMember
import consul4s.model.catalog.{CatalogNode, CatalogNodeServiceList, CatalogService, Node}
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import sttp.client.ResponseAs
import sttp.client.json4s._
import org.json4s._
import org.json4s.jackson.Serialization

package object json4s extends Agent with Catalog with Common with Event with Health with KV with Transaction {
//  implicit val serialization: Serialization.type = Serialization
//  val serializers = agentAllSerializers :::
//    catalogAllSerializers :::
//    commonAllSerializers :::
//    eventAllSerializers :::
//    healthAllSerializers :::
//    kvAllSerializers :::
//    transactionAllSerializers
//
//  implicit val formats = Serialization.formats(NoTypeHints) ++ serializers
}
