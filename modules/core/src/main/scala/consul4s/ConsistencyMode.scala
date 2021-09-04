package consul4s

import enumeratum.values._

sealed abstract class ConsistencyMode(val value: String) extends StringEnumEntry

object ConsistencyMode extends StringEnum[ConsistencyMode] {
  val values = findValues

  /**
   * If not specified, the default is strongly consistent in almost all cases. However, there is a small window in which a new leader may be
   * elected during which the old leader may service stale values. The trade-off is fast reads but potentially stale values. The condition
   * resulting in stale reads is hard to trigger, and most clients should not need to worry about this case. Also, note that this race
   * condition only applies to reads, not writes.
   */
  case object Default extends ConsistencyMode("default")

  /**
   * This mode is strongly consistent without caveats. It requires that a leader verify with a quorum of peers that it is still leader. This
   * introduces an additional round-trip to all server nodes. The trade-off is increased latency due to an extra round trip. Most clients
   * should not use this unless they cannot tolerate a stale read.
   */
  case object Consistent extends ConsistencyMode("consistent")

  /**
   * This mode allows any server to service the read regardless of whether it is the leader. This means reads can be arbitrarily stale;
   * however, results are generally consistent to within 50 milliseconds of the leader. The trade-off is very fast and scalable reads with a
   * higher likelihood of stale values. Since this mode allows reads without a leader, a cluster that is unavailable will still be able to
   * respond to queries.
   */
  case object Stale extends ConsistencyMode("stale")
}
