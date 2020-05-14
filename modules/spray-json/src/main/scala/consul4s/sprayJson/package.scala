package consul4s

import consul4s.model.agent._
import consul4s.model.catalog._
import consul4s.model.health.{HealthCheck, ServiceEntry}
import consul4s.model.kv.KVPair
import consul4s.sprayJson.model._
import sttp.client.ResponseAs
import sttp.client.sprayJson._

package object sprayJson extends Agent with Catalog with Common with Event with Health with KV with Transaction {}
