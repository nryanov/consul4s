package consul4s.model.agent

final case class AgentMember(
  Name: String,
  Addr: String,
  Port: Int,
  Tags: Map[String, String],
  Status: Int,
  ProtocolMin: Int,
  ProtocolMax: Int,
  ProtocolCur: Int,
  DelegateMin: Int,
  DelegateMax: Int,
  DelegateCur: Int
)
