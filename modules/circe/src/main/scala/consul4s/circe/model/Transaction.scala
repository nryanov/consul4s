package consul4s.circe.model

import consul4s.model.agent.AgentService
import consul4s.model.catalog.{CatalogService, Node}
import consul4s.model.health.HealthCheck
import consul4s.model.kv.KVPair
import consul4s.model.transaction._
import io.circe.Decoder.Result
import io.circe._

trait Transaction { this: Health with KV with Catalog with Agent =>

}
