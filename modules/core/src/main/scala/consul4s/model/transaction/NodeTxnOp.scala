package consul4s.model.transaction

import consul4s.model.catalog.Node

final case class NodeTxnOp(verb: NodeOp, node: Node)
