package consul4s.model.agent

final case class MemberInfo(
  name: String,
  address: String,
  port: Int,
  tags: Option[Map[String, String]],
  status: Int,
  protocolMin: Int,
  protocolMax: Int,
  protocolCur: Int,
  delegateMin: Int,
  delegateMax: Int,
  delegateCur: Int
)
