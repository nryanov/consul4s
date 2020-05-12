package consul4s.model.agent

final case class AgentCheckRegistration(
  id: Option[String],
  name: Option[String],
  notes: Option[String],
  serviceId: Option[String],
  // todo: ?
  agentServiceCheck: AgentServiceCheck,
  namespace: Option[String]
)

/*
// AgentCheckRegistration is used to register a new check
type AgentCheckRegistration struct {
	ID        string `json:",omitempty"`
	Name      string `json:",omitempty"`
	Notes     string `json:",omitempty"`
	ServiceID string `json:",omitempty"`
	AgentServiceCheck
	Namespace string `json:",omitempty"`
}
 */
