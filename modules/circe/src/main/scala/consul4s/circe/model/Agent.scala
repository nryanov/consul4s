package consul4s.circe.model

import consul4s.model.agent._
import consul4s.model.catalog.ServiceAddress
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import consul4s.model.{ServiceKind, Status}
import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe._

trait Agent { this: Catalog with Health with Common =>

}
