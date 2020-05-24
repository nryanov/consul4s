package consul4s.v1.api

import consul4s.ConsistencyMode
import consul4s.model.transaction.{TxResults, TxTask}
import sttp.client._

trait Transaction[F[_]] { this: ConsulApi[F] =>
  // PUT	/txn
  /**
   *
   * @param txTasks
   * @param dc
   * @param consistencyMode - For read-only transactions
   * @param token
   * @return
   */
  def executeTx(
    txTasks: List[TxTask],
    dc: Option[String] = None,
    consistencyMode: ConsistencyMode = ConsistencyMode.Default,
    token: Option[String] = None
  ): F[Result[TxResults]] = {
    val requestTemplate = basicRequest.put(addConsistencyMode(uri"$url/txn?dc=$dc", consistencyMode))
    val request = requestTemplate.copy(response = jsonDecoder.asTxResults).body(jsonEncoder.txTasksToJson(txTasks))

    sendRequest(request, token)
  }
}
