package consul4s

import consul4s.model.{KeyValue, NodeCheck, NodeForService, NodeInfo, ServiceCheck, ServiceInfo, State}
import sttp.client.ResponseAs
import sttp.client.circe._
import io.circe._

package object circe {
  private implicit val keyValueDecoder: Decoder[KeyValue] = new Decoder[KeyValue] {
    final def apply(c: HCursor): Decoder.Result[KeyValue] =
      for {
        createIndex <- c.downField("CreateIndex").as[Long]
        modifyIndex <- c.downField("ModifyIndex").as[Long]
        lockIndex <- c.downField("LockIndex").as[Long]
        key <- c.downField("Key").as[String]
        valueBase64 <- c.downField("Value").as[String]
        flags <- c.downField("Flags").as[Int]
      } yield KeyValue(createIndex, modifyIndex, lockIndex, key, valueBase64, flags)
  }

  private implicit val nodeCheckDecoder: Decoder[NodeCheck] = new Decoder[NodeCheck] {
    final def apply(c: HCursor): Decoder.Result[NodeCheck] =
      for {
        id <- c.downField("ID").as[String]
        node <- c.downField("Node").as[String]
        checkId <- c.downField("CheckID").as[String]
        name <- c.downField("Name").as[String]
        status <- c.downField("Status").as[String]
        notes <- c.downField("Notes").as[String]
        output <- c.downField("Output").as[String]
        serviceId <- c.downField("ServiceID").as[String]
        serviceName <- c.downField("ServiceName").as[String]
        serviceTags <- c.downField("ServiceTags").as[List[String]]
        namespace <- c.downField("Namespace").as[Option[String]]
      } yield NodeCheck(id, node, checkId, name, State.withValue(status), notes, output, serviceId, serviceName, serviceTags, namespace)
  }

  private implicit val serviceCheckDecoder: Decoder[ServiceCheck] = new Decoder[ServiceCheck] {
    final def apply(c: HCursor): Decoder.Result[ServiceCheck] =
      for {
        node <- c.downField("Node").as[String]
        checkId <- c.downField("CheckID").as[String]
        name <- c.downField("Name").as[String]
        status <- c.downField("Status").as[String]
        notes <- c.downField("Notes").as[String]
        output <- c.downField("Output").as[String]
        serviceId <- c.downField("ServiceID").as[String]
        serviceName <- c.downField("ServiceName").as[String]
        serviceTags <- c.downField("ServiceTags").as[List[String]]
        namespace <- c.downField("Namespace").as[Option[String]]
      } yield ServiceCheck(node, checkId, name, State.withValue(status), notes, output, serviceId, serviceName, serviceTags, namespace)
  }

  private implicit val nodeInfoDecoder: Decoder[NodeInfo] = new Decoder[NodeInfo] {
    final def apply(c: HCursor): Decoder.Result[NodeInfo] =
      for {
        id <- c.downField("ID").as[String]
        address <- c.downField("Node").as[String]
        node <- c.downField("Address").as[String]
        datacenter <- c.downField("Datacenter").as[String]
        taggedAddresses <- c.downField("TaggedAddresses").as[Map[String, String]]
        meta <- c.downField("Meta").as[Map[String, String]]
      } yield NodeInfo(id, address, node, datacenter, taggedAddresses, meta)
  }

  implicit val circeJsonDecoder = new JsonDecoder {
    override def asBooleanUnsafe: ResponseAs[Boolean, Nothing] = asJsonAlwaysUnsafe[Boolean]

    override def asNodeChecksOption: ResponseAs[Option[List[NodeCheck]], Nothing] = asJsonAlways[List[NodeCheck]].map(_.toOption)

    override def asServiceChecksOption: ResponseAs[Option[List[ServiceCheck]], Nothing] = asJsonAlways[List[ServiceCheck]].map(_.toOption)

    override def asNodeChecksUnsafe: ResponseAs[List[NodeCheck], Nothing] = asJsonAlwaysUnsafe[List[NodeCheck]]

    override def asServiceChecksUnsafe: ResponseAs[List[ServiceCheck], Nothing] = asJsonAlwaysUnsafe[List[ServiceCheck]]

    override def asStringUnsafe: ResponseAs[String, Nothing] = asJsonAlwaysUnsafe[String]

    override def asStringListUnsafe: ResponseAs[List[String], Nothing] = asJsonAlwaysUnsafe[List[String]]

    override def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing] = asJsonAlways[List[KeyValue]].map(_.toOption)

    override def asStringOption: ResponseAs[Option[String], Nothing] = asJsonAlways[String].map(_.toOption)

    override def asStringListOption: ResponseAs[Option[List[String]], Nothing] = asJsonAlways[List[String]].map(_.toOption)

    override def asNodeInfosOption: ResponseAs[Option[List[NodeInfo]], Nothing] = asJsonAlways[List[NodeInfo]].map(_.toOption)

    override def asNodeInfosUnsafe: ResponseAs[List[NodeInfo], Nothing] = asJsonAlwaysUnsafe[List[NodeInfo]]

    override def asMapOption: ResponseAs[Option[Map[String, String]], Nothing] = asJsonAlways[Map[String, String]].map(_.toOption)

    override def asMapUnsafe: ResponseAs[Map[String, String], Nothing] = asJsonAlwaysUnsafe[Map[String, String]]

    override def asMapMultipleValuesOption: ResponseAs[Option[Map[String, List[String]]], Nothing] =
      asJsonAlways[Map[String, List[String]]].map(_.toOption)

    override def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing] = asJsonAlwaysUnsafe[Map[String, List[String]]]

    override def asServicesInfoOption: ResponseAs[Option[List[ServiceInfo]], Nothing] = ???

    override def asServicesInfoUnsafe: ResponseAs[List[ServiceInfo], Nothing] = ???

    override def asNodesForServiceOption: ResponseAs[Option[List[NodeForService]], Nothing] = ???

    override def asNodesForServiceUnsafe: ResponseAs[List[NodeForService], Nothing] = ???
  }
}
