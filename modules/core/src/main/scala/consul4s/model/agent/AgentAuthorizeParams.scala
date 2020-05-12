package consul4s.model.agent

final case class AgentAuthorizeParams(target: String, clientCertUri: String, clientCertSerial: String)
