package consul4s.json4s.model

import consul4s.model.transaction.TxResults.{TxError, TxResult}
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction._
import org.json4s.JsonAST._
import org.json4s.{CustomSerializer, FieldSerializer}
import org.json4s.FieldSerializer._

trait Transaction {
  class KVOpSerializer
      extends CustomSerializer[KVOp](
        implicit format =>
          (
            {
              case JString(value) => KVOp.withValue(value)
            }, {
              case op: KVOp => JString(op.value)
            }
          )
      )

  class CheckOpSerializer
      extends CustomSerializer[CheckOp](
        implicit format =>
          (
            {
              case JString(value) => CheckOp.withValue(value)
            }, {
              case op: CheckOp => JString(op.value)
            }
          )
      )

  class NodeOpSerializer
      extends CustomSerializer[NodeOp](
        implicit format =>
          (
            {
              case JString(value) => NodeOp.withValue(value)
            }, {
              case op: NodeOp => JString(op.value)
            }
          )
      )

  class ServiceOpSerializer
      extends CustomSerializer[ServiceOp](
        implicit format =>
          (
            {
              case JString(value) => ServiceOp.withValue(value)
            }, {
              case op: ServiceOp => JString(op.value)
            }
          )
      )

  val txResultFormat = FieldSerializer[TxResult](
    Map(),
    renameFrom("KV", "kv").orElse(renameFrom("Node", "node")).orElse(renameFrom("Service", "service")).orElse(renameFrom("Check", "check"))
  )
  val txErrorFormat = FieldSerializer[TxError](Map(), renameFrom("OpIndex", "opIndex").orElse(renameFrom("What", "what")))
  val txResultsFormat = FieldSerializer[TxResults](Map(), renameFrom("Results", "results").orElse(renameFrom("Errors", "errors")))

  val kvTaskFormat = FieldSerializer[KVTask](
    renameTo("verb", "Verb")
      .orElse(renameTo("key", "Key"))
      .orElse(renameTo("value", "Value"))
      .orElse(renameTo("flags", "Flags").orElse(renameTo("index", "Index")).orElse(renameTo("session", "Session"))),
    Map()
  )
  val serviceTaskFormat = FieldSerializer[ServiceTask](
    renameTo("verb", "Verb").orElse(renameTo("node", "Node")).orElse(renameTo("service", "Service")),
    Map()
  )
  val nodeTaskFormat = FieldSerializer[NodeTask](
    renameTo("verb", "Verb").orElse(renameTo("node", "Node")),
    Map()
  )
  val nodeDefinitionFormat = FieldSerializer[NodeDefinition](
    renameTo("node", "Node")
      .orElse(renameTo("address", "Address"))
      .orElse(renameTo("id", "ID"))
      .orElse(renameTo("datacenter", "Datacenter"))
      .orElse(renameTo("taggedAddresses", "TaggedAddresses"))
      .orElse(renameTo("nodeMeta", "NodeMeta")),
    Map()
  )
  val checkTaskFormat = FieldSerializer[CheckTask](
    renameTo("verb", "Verb").orElse(renameTo("check", "Check")),
    Map()
  )
  val txTaskFormat = FieldSerializer[TxTask](
    renameTo("kv", "KV").orElse(renameTo("node", "Node")).orElse(renameTo("service", "Service")).orElse(renameTo("check", "Check")),
    Map()
  )

  val transactionAllSerializers = List(
    new KVOpSerializer,
    new CheckOpSerializer,
    new NodeOpSerializer,
    new ServiceOpSerializer
  )

  val transactionFieldAllSerializers = List(
    txResultFormat,
    txErrorFormat,
    txResultsFormat,
    kvTaskFormat,
    serviceTaskFormat,
    nodeTaskFormat,
    nodeDefinitionFormat,
    checkTaskFormat,
    txTaskFormat
  )
}
