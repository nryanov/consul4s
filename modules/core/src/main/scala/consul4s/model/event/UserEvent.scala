package consul4s.model.event

final case class UserEvent(
  id: String,
  name: String,
  payload: Option[Array[Byte]],
  nodeFilter: String,
  serviceFilter: String,
  tagFilter: String,
  version: Int,
  lTime: Long
)
