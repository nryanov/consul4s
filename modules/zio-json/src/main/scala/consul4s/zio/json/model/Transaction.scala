package consul4s.zio.json.model

import consul4s.model.agent.Service
import consul4s.model.catalog.{NewCatalogService, Node}
import consul4s.model.health.{HealthCheck, NewHealthCheck}
import consul4s.model.kv.KVPair
import consul4s.model.transaction.TxResults._
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction._
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Transaction { this: Common with Agent with Health with KV with Catalog =>
  private[zio] case class TxResultsRepr(Results: Option[List[TxResult]], Errors: Option[List[TxError]])

  private[zio] case class TxResultRepr(KV: Option[KVPair], Node: Option[Node], Service: Option[Service], Check: Option[HealthCheck])

  private[zio] case class TxErrorRepr(OpIndex: Long, What: String)

  private[zio] case class KVTaskRepr(
    Verb: KVOp,
    Key: String,
    Value: Option[String] = None,
    Flags: Option[Int] = None,
    Index: Option[Int] = None,
    Session: Option[String] = None
  )

  private[zio] case class ServiceTaskRepr(
    Verb: ServiceOp,
    Node: String,
    Service: NewCatalogService
  )

  private[zio] case class NodeTaskRepr(
    Verb: NodeOp,
    Node: NodeDefinition
  )

  private[zio] case class NodeDefinitionRepr(
    Node: String,
    Address: String,
    ID: Option[String] = None,
    Datacenter: Option[String] = None,
    TaggedAddresses: Option[Map[String, String]] = None,
    NodeMeta: Option[Map[String, String]] = None
  )

  private[zio] case class CheckTaskRepr(
    Verb: CheckOp,
    Check: NewHealthCheck
  )

  private[zio] case class TxTaskRepr(
    KV: Option[KVTask] = None,
    Node: Option[NodeTask] = None,
    Service: Option[ServiceTask] = None,
    Check: Option[CheckTask] = None
  )

  implicit val kvOpCodec: JsonCodec[KVOp] = JsonCodec.string.xmap(v => KVOp.withValue(v), _.value)
  implicit val checkOpCodec: JsonCodec[CheckOp] = JsonCodec.string.xmap(v => CheckOp.withValue(v), _.value)
  implicit val nodeOpCodec: JsonCodec[NodeOp] = JsonCodec.string.xmap(v => NodeOp.withValue(v), _.value)
  implicit val serviceOpCodec: JsonCodec[ServiceOp] = JsonCodec.string.xmap(v => ServiceOp.withValue(v), _.value)

  implicit val txErrorCodec: JsonCodec[TxError] = ConverterMacros.derive[TxErrorRepr, TxError]
  implicit val txResultCodec: JsonCodec[TxResult] = ConverterMacros.derive[TxResultRepr, TxResult]
  implicit val txResultsCodec: JsonCodec[TxResults] = ConverterMacros.derive[TxResultsRepr, TxResults]
  implicit val kvTaskCodec: JsonCodec[KVTask] = ConverterMacros.derive[KVTaskRepr, KVTask]
  implicit val checkTaskCodec: JsonCodec[CheckTask] = ConverterMacros.derive[CheckTaskRepr, CheckTask]
  implicit val nodeDefinitionCodec: JsonCodec[NodeDefinition] = ConverterMacros.derive[NodeDefinitionRepr, NodeDefinition]
  implicit val nodeTaskCodec: JsonCodec[NodeTask] = ConverterMacros.derive[NodeTaskRepr, NodeTask]
  implicit val serviceTaskCodec: JsonCodec[ServiceTask] = ConverterMacros.derive[ServiceTaskRepr, ServiceTask]
  implicit val TxTaskCodec: JsonCodec[TxTask] = ConverterMacros.derive[TxTaskRepr, TxTask]
}
