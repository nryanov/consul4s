package consul4s.model.agent

final case class AgentMember(
  name: String,
  addr: String,
  port: Int,
  tags: Map[String, String],
  status: Int,
  protocolMin: Int,
  protocolMax: Int,
  protocolCur: Int,
  delegateMin: Int,
  delegateMax: Int,
  delegateCur: Int
)
