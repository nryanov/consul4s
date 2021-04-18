package consul4s.model.transaction

import TxTask._
import consul4s.model.catalog.NewCatalogService
import consul4s.model.health.NewHealthCheck

final case class TxTask(
  KV: Option[KVTask] = None,
  Node: Option[NodeTask] = None,
  Service: Option[ServiceTask] = None,
  Check: Option[CheckTask] = None
)

object TxTask {
  final case class KVTask(
    Verb: KVOp,
    Key: String,
    Value: Option[String] = None,
    Flags: Option[Int] = None,
    Index: Option[Int] = None,
    Session: Option[String] = None
  )

  final case class ServiceTask(
    Verb: ServiceOp,
    Node: String,
    Service: NewCatalogService
  )

  final case class NodeTask(
    Verb: NodeOp,
    Node: NodeDefinition
  )

  final case class NodeDefinition(
    Node: String,
    Address: String,
    ID: Option[String] = None,
    Datacenter: Option[String] = None,
    TaggedAddresses: Option[Map[String, String]] = None,
    NodeMeta: Option[Map[String, String]] = None
  )

  final case class CheckTask(
    Verb: CheckOp,
    Check: NewHealthCheck
  )

}
