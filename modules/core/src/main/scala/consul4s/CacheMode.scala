package consul4s

sealed trait CacheMode

case object NoCache extends CacheMode

/**
 * https://www.consul.io/api-docs/features/caching#simple-caching
 * @param cacheControlHeader
 */
final case class SimpleCache(cacheControlHeader: Option[String] = None) extends CacheMode

/**
 * HTTP Cache-Control headers are ignored in this mode since the cache is being actively updated and has different semantics to a typical
 * passive cache
 */
case object BackgroundRefreshCache extends CacheMode
