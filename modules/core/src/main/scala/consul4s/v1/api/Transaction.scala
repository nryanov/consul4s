package consul4s.v1.api

import consul4s.ConsistencyMode
import consul4s.model.transaction.{TxResults, TxTask}
import sttp.client3._

trait Transaction[F[_]] { this: ConsulApi[F] =>

  /**
   * PUT /txn This endpoint permits submitting a list of operations to apply to Consul inside of a transaction. If any operation fails, the
   * transaction is rolled back and none of the changes are applied.
   * @param txTasks
   *   - operation list. Up to 64 operations may be present in a single transaction.
   * @param dc
   *   - Specifies the datacenter to query. This will default to the datacenter of the agent being queried. This is specified as part of the
   *   URL as a query parameter.
   * @param consistencyMode
   *   - For read-only transactions. For more info see [[ConsistencyMode]]
   * @param token
   *   - consul token
   * @return
   *   - transaction results. If transaction was roll-backed then errors field will not be empty/
   */
  def createTransaction(
    txTasks: List[TxTask],
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[TxResults]] = {
    val requestTemplate = basicRequest.put(addConsistencyMode(uri"$url/txn?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asTxResults)

    saveSendRequest(request, jsonEncoder.txTasksToJson(txTasks), token)
  }
}
