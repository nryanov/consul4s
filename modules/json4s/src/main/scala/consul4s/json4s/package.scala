package consul4s

import consul4s.model.{KeyValue, NodeCheck, ServiceCheck, State}
import sttp.client.ResponseAs
import sttp.client.json4s._
import org.json4s._
import org.json4s.native.Serialization

package object json4s {
  class KeyValueSerializer
      extends CustomSerializer[KeyValue](
        format =>
          (
            {
              case JObject(
                  JField("CreateIndex", JLong(f1)) ::
                    JField("ModifyIndex", JLong(f2)) ::
                    JField("LockIndex", JLong(f3)) ::
                    JField("Key", JString(f4)) ::
                    JField("Value", JString(f5)) ::
                    JField("Flags", JInt(f6)) ::
                    Nil
                  ) =>
                KeyValue(f1, f2, f3, f4, f5, f6.intValue)
            }, {
              case _: KeyValue => JObject()
            }
          )
      )

  class NodeCheckSerializer
      extends CustomSerializer[NodeCheck](
        format =>
          (
            {
              case JObject(
                  JField("ID", JString(f1)) ::
                    JField("Node", JString(f2)) ::
                    JField("CheckID", JString(f3)) ::
                    JField("Name", JString(f4)) ::
                    JField("Status", JString(f5)) ::
                    JField("Notes", JString(f6)) ::
                    JField("Output", JString(f7)) ::
                    JField("ServiceID", JString(f8)) ::
                    JField("ServiceName", JString(f9)) ::
                    JField("ServiceTags", JArray(items)) ::
                    JField("Namespace", f11: JString) ::
                    Nil
                  ) =>
                NodeCheck(
                  f1,
                  f2,
                  f3,
                  f4,
                  State.withValue(f5),
                  f6,
                  f7,
                  f8,
                  f9,
                  items.map {
                    case JString(value) => value
                    case x              => throw new MappingException(s"Cannot convert $x to String")
                  },
                  f11.toOption.map {
                    case JString(value) => value
                  }
                )
            }, {
              case _: NodeCheck => JObject()
            }
          )
      )

  class ServiceCheckSerializer
      extends CustomSerializer[ServiceCheck](
        format =>
          (
            {
              case JObject(
                  JField("Node", JString(f2)) ::
                    JField("CheckID", JString(f3)) ::
                    JField("Name", JString(f4)) ::
                    JField("Status", JString(f5)) ::
                    JField("Notes", JString(f6)) ::
                    JField("Output", JString(f7)) ::
                    JField("ServiceID", JString(f8)) ::
                    JField("ServiceName", JString(f9)) ::
                    JField("ServiceTags", JArray(items)) ::
                    JField("Namespace", f11: JString) ::
                    Nil
                  ) =>
                ServiceCheck(
                  f2,
                  f3,
                  f4,
                  State.withValue(f5),
                  f6,
                  f7,
                  f8,
                  f9,
                  items.map {
                    case JString(value) => value
                    case x              => throw new MappingException(s"Cannot convert $x to String")
                  },
                  f11.toOption.map {
                    case JString(value) => value
                  }
                )
            }, {
              case _: ServiceCheck => JObject()
            }
          )
      )

  private implicit val serialization = org.json4s.native.Serialization
  implicit val formats = Serialization.formats(NoTypeHints) + new KeyValueSerializer + new NodeCheckSerializer + new ServiceCheckSerializer

  implicit val json4sJsonDecoder = new JsonDecoder {
    override def asBooleanUnsafe: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asNodeChecksOption: ResponseAs[Option[List[NodeCheck]], Nothing] = asJsonAlways[List[NodeCheck]].map(_.toOption)

    override def asServiceChecksOption: ResponseAs[Option[List[ServiceCheck]], Nothing] = asJsonAlways[List[ServiceCheck]].map(_.toOption)

    override def asNodeChecksUnsafe: ResponseAs[List[NodeCheck], Nothing] = asJsonAlwaysUnsafe[List[NodeCheck]]

    override def asServiceChecksUnsafe: ResponseAs[List[ServiceCheck], Nothing] = asJsonAlwaysUnsafe[List[ServiceCheck]]

    override def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlways[List[KeyValue]].map(_.toOption)

    override def asStringUnsafe: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringListUnsafe: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]

    override def asStringOption: ResponseAs[Option[String], Nothing] = asJsonAlways[String].map(_.toOption)

    override def asStringListOption: ResponseAs[Option[List[String]], Nothing] = asJsonAlways[List[String]].map(_.toOption)
  }
}
