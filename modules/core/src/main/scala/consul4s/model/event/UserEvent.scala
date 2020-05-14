package consul4s.model.event

final case class UserEvent(
  ID: String,
  Name: String,
  Payload: Option[Array[Byte]],
  NodeFilter: String,
  ServiceFilter: String,
  TagFilter: String,
  Version: Int,
  LTime: Long
)
