package consul4s.json4s.model

import consul4s.model.kv.KVPair
import org.json4s.{CustomSerializer, JObject}

trait KV {
  val kvPairSerializer = new CustomSerializer[KVPair](
    implicit format =>
      (
        {
          case json: JObject =>
            KVPair(
              (json \ "Key").extract[String],
              (json \ "CreateIndex").extract[Long],
              (json \ "ModifyIndex").extract[Long],
              (json \ "LockIndex").extract[Long],
              (json \ "Flags").extract[Int],
              (json \ "Value").extract[Array[Byte]],
              (json \ "Session").extract[String],
              (json \ "Namespace").extract[Option[String]]
            )
        }, {
          case _: KVPair => JObject()
        }
      )
  )
}
