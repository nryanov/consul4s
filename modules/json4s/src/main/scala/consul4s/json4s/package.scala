package consul4s

import consul4s.model.{KeyValue, MemberInfo, NodeCheck, NodeForService, NodeInfo, ServiceCheck, ServiceInfo, State}
import sttp.client.ResponseAs
import sttp.client.json4s._
import org.json4s._
import org.json4s.jackson.Serialization

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

  class ServiceInfoSerializer
      extends CustomSerializer[ServiceInfo](
        implicit format =>
          (
            {
              case json: JObject =>
                ServiceInfo(
                  (json \ "ID").extract[String],
                  (json \ "Service").extract[String],
                  (json \ "Tags").extract[List[String]],
                  (json \ "Address").extract[String],
                  (json \ "Meta").extract[Map[String, String]],
                  (json \ "Port").extract[Int],
                  (json \ "Weights").extract[Map[String, Int]],
                  (json \ "EnableTagOverride").extract[Boolean],
                  (json \ "Proxy").extract[Map[String, Map[String, String]]],
                  (json \ "Connect").extract[Map[String, String]]
                )
            }, {
              case _: ServiceInfo => JObject()
            }
          )
      )

  class NodeForServiceSerializer
      extends CustomSerializer[NodeForService](
        implicit format =>
          (
            {
              case json: JObject =>
                NodeForService(
                  (json \ "Node").extract[NodeInfo],
                  (json \ "Service").extract[ServiceInfo],
                  (json \ "Checks").extract[List[ServiceCheck]]
                )
            }, {
              case _: NodeForService => JObject()
            }
          )
      )

  class MemberInfoSerializer
      extends CustomSerializer[MemberInfo](
        implicit format =>
          (
            {
              case json: JObject =>
                MemberInfo(
                  (json \ "Name").extract[String],
                  (json \ "Addr").extract[String],
                  (json \ "Port").extract[Int],
                  (json \ "Tags").extract[Map[String, String]],
                  (json \ "Status").extract[Int],
                  (json \ "ProtocolMin").extract[Int],
                  (json \ "ProtocolMax").extract[Int],
                  (json \ "ProtocolCur").extract[Int],
                  (json \ "DelegateMin").extract[Int],
                  (json \ "DelegateMax").extract[Int],
                  (json \ "DelegateCur").extract[Int]
                )
            }, {
              case _: MemberInfo => JObject()
            }
          )
      )

  private implicit val serialization = org.json4s.jackson.Serialization
  implicit val formats = Serialization.formats(NoTypeHints) +
    new KeyValueSerializer +
    new NodeCheckSerializer +
    new ServiceCheckSerializer +
    new NodeInfoSerializer +
    new ServiceInfoSerializer +
    new MemberInfoSerializer

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

    override def asServicesInfoOption: ResponseAs[Option[List[ServiceInfo]], Nothing] = asJsonAlways[List[ServiceInfo]].map(_.toOption)

    override def asServicesInfoUnsafe: ResponseAs[List[ServiceInfo], Nothing] = asJsonAlwaysUnsafe[List[ServiceInfo]]

    override def asNodesForServiceOption: ResponseAs[Option[List[NodeForService]], Nothing] =
      asJsonAlways[List[NodeForService]].map(_.toOption)

    override def asNodesForServiceUnsafe: ResponseAs[List[NodeForService], Nothing] = asJsonAlwaysUnsafe[List[NodeForService]]

    override def asMembersInfoOption: ResponseAs[Option[List[MemberInfo]], Nothing] = asJsonAlways[List[MemberInfo]].map(_.toOption)

    override def asMembersInfoUnsafe: ResponseAs[List[MemberInfo], Nothing] = asJsonAlwaysUnsafe[List[MemberInfo]]

    override def asServicesInfoMapOption: ResponseAs[Option[Map[String, ServiceInfo]], Nothing] =
      asJsonAlways[Map[String, ServiceInfo]].map(_.toOption)

    override def asServicesInfoMapUnsafe: ResponseAs[Map[String, ServiceInfo], Nothing] = asJsonAlwaysUnsafe[Map[String, ServiceInfo]]
  }
}
