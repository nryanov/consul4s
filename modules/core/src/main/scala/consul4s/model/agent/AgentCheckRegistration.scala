package consul4s.model.agent

final case class AgentCheckRegistration(
  id: Option[String],
  name: Option[String],
  notes: Option[String],
  serviceId: Option[String],
  agentServiceCheck: Option[AgentServiceCheck],
  namespace: Option[String]
)
