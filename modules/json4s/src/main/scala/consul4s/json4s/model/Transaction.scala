package consul4s.json4s.model

import consul4s.model.agent.AgentService
import consul4s.model.catalog.{CatalogService, Node}
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction.{TxnError, _}
import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, JObject}

trait Transaction {

  val transactionAllSerializers = List(
    )
}
