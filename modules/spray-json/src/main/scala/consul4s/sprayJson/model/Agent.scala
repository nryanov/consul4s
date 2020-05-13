package consul4s.sprayJson.model

import consul4s.model.agent._
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Agent extends DefaultJsonProtocol { this: Health with Catalog with Common =>

}
