package consul4s.model.transaction

class TxnOp {}

/*
// TxnOp is the internal format we send to Consul. Currently only K/V and
// check operations are supported.
type TxnOp struct {
	KV      *KVTxnOp
	Node    *NodeTxnOp
	Service *ServiceTxnOp
	Check   *CheckTxnOp
}
 */
