package consul4s.json4s.model

import consul4s.model.Status
import consul4s.model.agent.AgentService
import consul4s.model.catalog.Node
import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
import org.json4s.{CustomSerializer, JObject}

trait Health {

  val healthAllSerializers = List(
    )
}
