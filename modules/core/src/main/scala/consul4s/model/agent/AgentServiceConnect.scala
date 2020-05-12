package consul4s.model.agent

final case class AgentServiceConnect(native: Option[Boolean], sedicarService: Option[AgentServiceRegistration])
