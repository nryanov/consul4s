package consul4s

import consul4s.model.{KeyValue, NodeCheck, ServiceCheck, State}
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
  }
}
