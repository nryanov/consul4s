package consul4s.model.transaction

import TxTask._
import consul4s.model.catalog.NewCatalogService
import consul4s.model.health.NewHealthCheck

final case class TxTask(
  kv: Option[KVTask] = None,
  node: Option[NodeTask] = None,
  service: Option[ServiceTask] = None,
  check: Option[CheckTask] = None
)

object TxTask {
  final case class KVTask(
    verb: KVOp,
    key: String,
    value: Option[String] = None,
    flags: Option[Int] = None,
    index: Option[Int] = None,
    session: Option[String] = None
  )

  final case class ServiceTask(
    verb: ServiceOp,
    node: String,
    service: NewCatalogService
  )

  final case class NodeTask(
    verb: NodeOp,
    node: NodeDefinition
  )

  final case class NodeDefinition(
    node: String,
    address: String,
    id: Option[String] = None,
    datacenter: Option[String] = None,
    taggedAddresses: Option[Map[String, String]] = None,
    nodeMeta: Option[Map[String, String]] = None
  )

  final case class CheckTask(
    verb: CheckOp,
    check: NewHealthCheck
  )

}
