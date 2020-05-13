package consul4s.circe.model

import consul4s.model.agent.{AgentCheck, AgentService, AgentServiceConnectProxyConfig}
import consul4s.model.catalog._
import consul4s.model.health.HealthCheck
import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe._

trait Catalog { this: Agent with Health =>

}
