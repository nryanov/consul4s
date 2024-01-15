package consul4s.model.agent

sealed abstract class UpstreamDestType(val value: String)

object UpstreamDestType {
  def unapply(v: String): Option[UpstreamDestType] = v match {
    case "service"        => Some(Service)
    case "prepared_query" => Some(PreparedQuery)
    case _                => None
  }

  case object Service extends UpstreamDestType("service")

  case object PreparedQuery extends UpstreamDestType("prepared_query")
}
