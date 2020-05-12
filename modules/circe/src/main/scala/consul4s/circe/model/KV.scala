package consul4s.circe.model

import consul4s.model.kv.KVPair
import io.circe.Decoder.Result
import io.circe._

trait KV {
  implicit val kvPairDecoder: Decoder[KVPair] = new Decoder[KVPair] {
    override def apply(c: HCursor): Result[KVPair] = for {
      key <- c.downField("Key").as[String]
      createIndex <- c.downField("CreateIndex").as[Long]
      modifyIndex <- c.downField("ModifyIndex").as[Long]
      lockIndex <- c.downField("LockIndex").as[Long]
      flags <- c.downField("Flags").as[Long]
      valueBase64 <- c.downField("Value").as[Array[Byte]]
      session <- c.downField("Session").as[String]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield KVPair(key, createIndex, modifyIndex, lockIndex, flags, valueBase64, session, namespace)
  }
}
