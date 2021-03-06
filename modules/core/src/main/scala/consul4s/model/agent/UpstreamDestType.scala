package consul4s.model.agent

import enumeratum.values.{StringEnum, StringEnumEntry}

sealed abstract class UpstreamDestType(val value: String) extends StringEnumEntry

object UpstreamDestType extends StringEnum[UpstreamDestType] {
  val values = findValues

  case object Service extends UpstreamDestType("service")

  case object PreparedQuery extends UpstreamDestType("prepared_query")
}
