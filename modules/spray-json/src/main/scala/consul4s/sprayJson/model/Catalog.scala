package consul4s.sprayJson.model

import consul4s.model.catalog._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Catalog extends DefaultJsonProtocol { this: Health with Agent =>

}
