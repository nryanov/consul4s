package consul4s.sprayJson.model

import consul4s.model.transaction._
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

trait Transaction extends DefaultJsonProtocol { this: Health with KV with Catalog with Agent =>

}
