package consul4s.json4s.model

import consul4s.model.transaction.TxResults.{TxError, TxResult}
import consul4s.model.transaction.TxTask._
import consul4s.model.transaction._
import org.json4s.JsonAST._
import org.json4s.{CustomSerializer, FieldSerializer}

trait Transaction {
  class KVOpSerializer
      extends CustomSerializer[KVOp](implicit format =>
        (
          { case JString(value) =>
            KVOp.withValue(value)
          },
          { case op: KVOp =>
            JString(op.value)
          }
        )
      )

  class CheckOpSerializer
      extends CustomSerializer[CheckOp](implicit format =>
        (
          { case JString(value) =>
            CheckOp.withValue(value)
          },
          { case op: CheckOp =>
            JString(op.value)
          }
        )
      )

  class NodeOpSerializer
      extends CustomSerializer[NodeOp](implicit format =>
        (
          { case JString(value) =>
            NodeOp.withValue(value)
          },
          { case op: NodeOp =>
            JString(op.value)
          }
        )
      )

  class ServiceOpSerializer
      extends CustomSerializer[ServiceOp](implicit format =>
        (
          { case JString(value) =>
            ServiceOp.withValue(value)
          },
          { case op: ServiceOp =>
            JString(op.value)
          }
        )
      )

  val txResultFormat: FieldSerializer[TxResult] = FieldSerializer[TxResult]()
  val txErrorFormat: FieldSerializer[TxError] = FieldSerializer[TxError]()
  val txResultsFormat: FieldSerializer[TxResults] = FieldSerializer[TxResults]()

  val kvTaskFormat: FieldSerializer[KVTask] = FieldSerializer[KVTask]()
  val serviceTaskFormat: FieldSerializer[ServiceTask] = FieldSerializer[ServiceTask]()
  val nodeTaskFormat: FieldSerializer[NodeTask] = FieldSerializer[NodeTask]()
  val nodeDefinitionFormat: FieldSerializer[NodeDefinition] = FieldSerializer[NodeDefinition]()
  val checkTaskFormat: FieldSerializer[CheckTask] = FieldSerializer[CheckTask]()
  val txTaskFormat: FieldSerializer[TxTask] = FieldSerializer[TxTask]()

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
