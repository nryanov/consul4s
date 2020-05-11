package consul4s

import consul4s.model.{KeyValue, NodeCheck, NodeInfo, ServiceCheck, State}
import sttp.client.ResponseAs
import sttp.client.sprayJson._
import spray.json._

package object sprayJson {
  private object JsonProtocol extends DefaultJsonProtocol {
    implicit object StateFormat extends RootJsonFormat[State] {
      override def write(obj: State): JsValue = JsString(obj.value)

      override def read(json: JsValue): State = json match {
        case JsString(value) => State.withValue(value)
        case _               => deserializationError("State expected")
      }
    }

    implicit val keyValueFormat = jsonFormat(KeyValue.apply, "CreateIndex", "ModifyIndex", "LockIndex", "Key", "Value", "Flags")
    implicit val nodeCheckFormat = jsonFormat(
      NodeCheck.apply,
      "ID",
      "Node",
      "CheckID",
      "Name",
      "Status",
      "Notes",
      "Output",
      "ServiceID",
      "ServiceName",
      "ServiceTags",
      "Namespace"
    )
    implicit val statusCheckFormat = jsonFormat(
      ServiceCheck.apply,
      "Node",
      "CheckID",
      "Name",
      "Status",
      "Notes",
      "Output",
      "ServiceID",
      "ServiceName",
      "ServiceTags",
      "Namespace"
    )
    implicit val nodeInfoFormat = jsonFormat(
      NodeInfo.apply,
      "ID",
      "Node",
      "Address",
      "Datacenter",
      "TaggedAddresses",
      "Meta"
    )
  }

  import JsonProtocol._

  implicit val sprayJsonDecoder = new JsonDecoder {
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
