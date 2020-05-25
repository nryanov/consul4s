package consul4s.json4s

import org.json4s.JsonAST._

package object model {
  private[model] def optionToValue(value: Option[String]): JValue =
    value.map(v => JString(v)).getOrElse(JNull)
}
