package consul4s.circe.model

import consul4s.model.kv.KVPair
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

trait KV {
  implicit val kvPairEncoder: Encoder[KVPair] = new Encoder[KVPair] {
    override def apply(a: KVPair): Json = Json.obj(
      ("Key", a.key.asJson),
      ("CreateIndex", a.createIndex.asJson),
      ("ModifyIndex", a.modifyIndex.asJson),
      ("LockIndex", a.lockIndex.asJson),
      ("Flags", a.flags.asJson),
      ("Value", a.value.asJson),
      ("Session", a.session.asJson)
    )
  }

  implicit val kvPairDecoder: Decoder[KVPair] = new Decoder[KVPair] {
    override def apply(c: HCursor): Result[KVPair] = for {
      key <- c.downField("Key").as[String]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
      lockIndex <- c.downField("LockIndex").as[Long]
      flags <- c.downField("Flags").as[Long]
      value <- c.downField("Value").as[Option[String]]
      session <- c.downField("Session").as[Option[String]]
    } yield KVPair(
      key,
      createIndex,
      modifyIndex,
      lockIndex,
      flags,
      value,
      session
    )
  }
}
