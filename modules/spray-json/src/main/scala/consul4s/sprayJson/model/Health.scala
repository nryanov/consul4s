package consul4s.sprayJson.model

import consul4s.model.health.{HealthCheck, HealthCheckDefinition, ServiceEntry}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Health extends DefaultJsonProtocol { this: Common with Agent with Catalog =>

}
