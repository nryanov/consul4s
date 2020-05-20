package consul4s.sprayJson.model

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Health extends DefaultJsonProtocol { this: Common with Agent with Catalog =>

}
