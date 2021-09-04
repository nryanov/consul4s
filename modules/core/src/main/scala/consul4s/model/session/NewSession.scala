package consul4s.model.session

import consul4s.model.SessionBehavior

/**
 * @param Node
 *   - Specifies the duration for the lock delay. This must be greater than 0. Example: "15s"
 * @param LockDelay
 *   - Specifies the name of the node. This must refer to a node that is already registered.
 * @param Name
 *   - Specifies a human-readable name for the session.
 * @param ID
 *   - session id (uuid)
 * @param Checks
 *   - specifies a list of associated health check IDs (commonly CheckID in API responses). It is highly recommended that, if you override
 *   this list, you include the default serfHealth.
 * @param Behavior
 *   - Controls the behavior to take when a session is invalidated.
 * @param TTL
 *   - Specifies the number of seconds (between 10s and 86400s). If provided, the session is invalidated if it is not renewed before the TTL
 *   expires. The lowest practical TTL should be used to keep the number of managed sessions low. When locks are forcibly expired, such as
 *   when following the leader election pattern in an application, sessions may not be reaped for up to double this TTL, so long TTL values
 *   (> 1 hour) should be avoided.
 */
final case class NewSession(
  Node: String,
  LockDelay: String,
  Name: Option[String] = None,
  ID: Option[String] = None,
  Checks: Option[List[String]] = Some(List("serfHealth")),
  Behavior: SessionBehavior = SessionBehavior.Release,
  TTL: Option[String] = None
)
