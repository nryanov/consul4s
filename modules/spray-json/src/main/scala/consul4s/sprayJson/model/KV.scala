package consul4s.sprayJson.model

import consul4s.model.kv.KVPair
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait KV extends DefaultJsonProtocol {}
