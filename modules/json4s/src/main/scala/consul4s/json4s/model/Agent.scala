package consul4s.json4s.model

import consul4s.model.{ServiceKind, Status}
import consul4s.model.agent._
import consul4s.model.catalog.ServiceAddress
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import org.json4s._
import org.json4s.JsonAST.JString

trait Agent {

  val agentAllSerializers = List(
    )
}
