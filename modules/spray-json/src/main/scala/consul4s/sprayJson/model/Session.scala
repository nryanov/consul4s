package consul4s.sprayJson.model

import consul4s.model.session._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Session extends DefaultJsonProtocol { this: Common =>
  implicit val newSessionFormat: RootJsonFormat[NewSession] = jsonFormat7(NewSession.apply)

  implicit val sessionIdFormat: RootJsonFormat[SessionId] = jsonFormat1(SessionId)

  implicit val sessionInfoFormat: RootJsonFormat[SessionInfo] = jsonFormat10(SessionInfo.apply)

}
