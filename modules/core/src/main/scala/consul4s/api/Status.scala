package consul4s.api

import sttp.client._

trait Status[F[_]] {
  // GET /status/leader
  def raftLeader(dc: Option[String] = None): F[Response[String]]

  // GET /status/peers
  def raftPeers(dc: Option[String] = None): F[Response[List[String]]]
}
