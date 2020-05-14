package consul4s.json4s.model

import consul4s.model.CheckStatus
import consul4s.model.agent._
import consul4s.model.catalog.Node
import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
import org.json4s.{CustomSerializer, JObject}

trait Health {

  val healthAllSerializers = List(
    )
}
