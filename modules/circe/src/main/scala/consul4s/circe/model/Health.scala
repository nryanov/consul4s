package consul4s.circe.model

import consul4s.model.Status
import consul4s.model.agent.AgentService
import consul4s.model.catalog.Node
import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
import io.circe.Decoder.Result
import io.circe._

trait Health { this: Common with Agent with Catalog =>

}
