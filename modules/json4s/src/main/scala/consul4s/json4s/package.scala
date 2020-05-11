package consul4s

import consul4s.model.{KeyValue, NodeCheck, NodeInfo, ServiceCheck, State}
import sttp.client.ResponseAs
import sttp.client.json4s._
import org.json4s._
import org.json4s.native.Serialization

package object json4s {
  class KeyValueSerializer
      extends CustomSerializer[KeyValue](
        implicit format =>
          (
            {
              case json: JObject =>
                KeyValue(
                  (json \ "CreateIndex").extract[Long],
                  (json \ "ModifyIndex").extract[Long],
                  (json \ "LockIndex").extract[Long],
                  (json \ "Key").extract[String],
                  (json \ "Value").extract[String],
                  (json \ "Flags").extract[Int]
                )
            }, {
              case _: KeyValue => JObject()
            }
          )
      )

  class NodeCheckSerializer
      extends CustomSerializer[NodeCheck](
        implicit format =>
          (
            {
              case json: JObject =>
                NodeCheck(
                  (json \ "ID").extract[String],
                  (json \ "Node").extract[String],
                  (json \ "CheckID").extract[String],
                  (json \ "Name").extract[String],
                  State.withValue((json \ "Status").extract[String]),
                  (json \ "Notes").extract[String],
                  (json \ "Output").extract[String],
                  (json \ "ServiceID").extract[String],
                  (json \ "ServiceName").extract[String],
                  (json \ "ServiceTags").extract[List[String]],
                  (json \ "Namespace").extract[Option[String]]
                )
            }, {
              case _: NodeCheck => JObject()
            }
          )
      )

  class ServiceCheckSerializer
      extends CustomSerializer[ServiceCheck](
        implicit format =>
          (
            {
              case json: JObject =>
                ServiceCheck(
                  (json \ "Node").extract[String],
                  (json \ "CheckID").extract[String],
                  (json \ "Name").extract[String],
                  State.withValue((json \ "Status").extract[String]),
                  (json \ "Notes").extract[String],
                  (json \ "Output").extract[String],
                  (json \ "ServiceID").extract[String],
                  (json \ "ServiceName").extract[String],
                  (json \ "ServiceTags").extract[List[String]],
                  (json \ "Namespace").extract[Option[String]]
                )
            }, {
              case _: ServiceCheck => JObject()
            }
          )
      )

  class NodeInfoSerializer
      extends CustomSerializer[NodeInfo](
        implicit format =>
          (
            {
              case json: JObject =>
                NodeInfo(
                  (json \ "ID").extract[String],
                  (json \ "Node").extract[String],
                  (json \ "Address").extract[String],
                  (json \ "Datacenter").extract[String],
                  (json \ "TaggedAddresses").extract[Map[String, String]],
                  (json \ "Meta").extract[Map[String, String]]
                )
            }, {
              case _: NodeInfo => JObject()
            }
          )
      )

  private implicit val serialization = org.json4s.native.Serialization
  implicit val formats = Serialization.formats(NoTypeHints) +
    new KeyValueSerializer +
    new NodeCheckSerializer +
    new ServiceCheckSerializer +
    new NodeInfoSerializer

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

    override def asNodeInfosOption: ResponseAs[Option[List[NodeInfo]], Nothing] = asJsonAlways[List[NodeInfo]].map(_.toOption)

    override def asNodeInfosUnsafe: ResponseAs[List[NodeInfo], Nothing] = asJsonAlwaysUnsafe[List[NodeInfo]]

    override def asMapOption: ResponseAs[Option[Map[String, String]], Nothing] = asJsonAlways[Map[String, String]].map(_.toOption)

    override def asMapUnsafe: ResponseAs[Map[String, String], Nothing] = asJsonAlwaysUnsafe[Map[String, String]]

    override def asMapMultipleValuesOption: ResponseAs[Option[Map[String, List[String]]], Nothing] =
      asJsonAlways[Map[String, List[String]]].map(_.toOption)

    override def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing] = asJsonAlwaysUnsafe[Map[String, List[String]]]
  }
}
