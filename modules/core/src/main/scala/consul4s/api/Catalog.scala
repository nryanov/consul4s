package consul4s.api

import consul4s.model.NodeInfo
import sttp.client.Response

trait Catalog[F[_]] {
  // GET	/catalog/datacenters
  def datacenters(): F[Response[List[String]]]

  // GET	/catalog/nodes
  def nodes(
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Response[List[NodeInfo]]]

  // GET	/catalog/services
  def services(
    dc: Option[String] = None,
    nodeMeta: Option[String] = None,
    ns: Option[String] = None
  ): F[Response[Map[String, List[String]]]]
}
