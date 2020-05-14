package consul4s.json4s.model

import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction._
import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, JObject}

trait Transaction {

  val transactionAllSerializers = List(
    )
}
