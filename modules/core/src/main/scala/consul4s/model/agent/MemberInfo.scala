package consul4s.model.agent

final case class MemberInfo(
  Name: String,
  Address: String,
  Port: Int,
  Tags: Option[Map[String, String]],
  Status: Int,
  ProtocolMin: Int,
  ProtocolMax: Int,
  ProtocolCur: Int,
  DelegateMin: Int,
  DelegateMax: Int,
  DelegateCur: Int
)
