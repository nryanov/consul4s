package consul4s.json4s.model

import consul4s.model.agent.{AgentCheck, AgentService, AgentServiceConnectProxyConfig}
import consul4s.model.catalog._
import consul4s.model.health.HealthCheck
import org.json4s.{CustomSerializer, JObject, NoTypeHints}

trait Catalog {

  val catalogAllSerializers = List(
    )
}
