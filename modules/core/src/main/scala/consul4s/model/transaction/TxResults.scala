package consul4s.model.transaction

import java.nio.charset.StandardCharsets
import java.util.Base64

import consul4s.model.agent.Weights
import consul4s.model.catalog.Node
import consul4s.model.health.HealthCheck
import TxResults._

final case class TxResults(Results: Option[List[TxResult]], Errors: Option[List[TxError]])

object TxResults {
  final case class TxResult(KV: Option[KVPairTx], Node: Option[Node], Service: Option[Service], Check: Option[HealthCheck])

  final case class TxError(OpIndex: Long, What: String)

  final case class KVPairTx(
    Key: String,
    CreateIndex: Long,
    ModifyIndex: Long,
    LockIndex: Long,
    Flags: Long,
    Value: Option[String],
    Session: Option[String]
  ) {
    lazy val decodedValue: Option[String] =
      Value.map(v => new String(Base64.getDecoder.decode(v.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
  }

  //todo: Weights -> common?
  final case class Service(
    ID: String,
    Service: String,
    Tags: Option[List[String]],
    Address: String,
    Meta: Option[Map[String, String]],
    Port: Int,
    Weights: Weights,
    EnableTagOverride: Boolean,
    CreateIndex: Long,
    ModifyIndex: Long
  )
}
