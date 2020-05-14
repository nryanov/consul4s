package consul4s.api

import consul4s.model.catalog._
import sttp.client._

trait Catalog[F[_]] { this: ConsulApi[F] =>
  // PUT /catalog/register
  def registerEntity(value: EntityRegistration): F[Response[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/register")
    val request = requestTemplate.copy(response = ignore).body(jsonEncoder.entityRegistrationToJson(value))

    val response = sttpBackend.send(request)
    response
  }

  // PUT /catalog/deregister
  // CatalogDeregistration: namespace > ns
  def deregisterEntity(value: EntityDeregistration): F[Response[Unit]] = {

    val requestTemplate = basicRequest.put(uri"$url/catalog/deregister")
    val request = requestTemplate.copy(response = ignore).body(jsonEncoder.entityDeregistrationToJson(value))

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/datacenters
  def datacenters(): F[Response[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/nodes
  def nodes(
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Response[List[Node]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val params = List(dcParam, nearParam, nodeMetaParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/nodes?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/services
  def services(
    dc: Option[String] = None,
    nodeMeta: Option[String] = None
  ): F[Response[Map[String, List[String]]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val params = List(dcParam, nodeMetaParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/services?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asMapMultipleValuesUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET /catalog/service/ + service
  def nodesInfoForService(
    service: String,
    dc: Option[String] = None,
    tag: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Response[List[ServiceInfo]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val params = List(dcParam, tagParam, nearParam, nodeMetaParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/service/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceInfoListUnsafe)

    val response = sttpBackend.send(request)
    response
  }

  // GET /catalog/connect/ + service
  def nodesInfoForConnectCapableService(
    service: String,
    dc: Option[String] = None,
    tag: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Response[List[ServiceInfo]]] = {
    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
    val tagParam = tag.map(v => s"tag=$v").getOrElse("")
    val nearParam = near.map(v => s"near=$v").getOrElse("")
    val nodeMetaParam = nodeMeta.map(v => s"node-meta=$v").getOrElse("")
    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
    val params = List(dcParam, tagParam, nearParam, nodeMetaParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")

    val requestTemplate = basicRequest.get(uri"$url/catalog/connect/$service?$params")
    val request = requestTemplate.copy(response = jsonDecoder.asServiceInfoListUnsafe)

    val response = sttpBackend.send(request)
    response
  }
//
//  // GET	/catalog/node/:node
//  def mapOfServicesForNode(
//    node: String,
//    dc: Option[String] = None,
//    filter: Option[String] = None
//  ): F[Response[Option[CatalogNode]]] = {
//    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
//    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
//    val params = List(dcParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")
//
//    val requestTemplate = basicRequest.get(uri"$url/catalog/node/$node?$params")
//    val request = requestTemplate.copy(response = jsonDecoder.asCatalogNodeOption)
//
//    val response = sttpBackend.send(request)
//    response
//  }
//
//  // GET	/catalog/node-services/:node
//  def listServicesForNode(
//    node: String,
//    dc: Option[String] = None,
//    filter: Option[String] = None
//  ): F[Response[Option[CatalogNodeServiceList]]] = {
//    val dcParam = dc.map(v => s"dc=$v").getOrElse("")
//    val filterParam = filter.map(v => s"filter=$v").getOrElse("")
//    val params = List(dcParam, filterParam).filterNot(_.isBlank).mkString("", "&", "")
//
//    val requestTemplate = basicRequest.get(uri"$url/catalog/node-services/$node?$params")
//    val request = requestTemplate.copy(response = jsonDecoder.asCatalogNodeServiceListOption)
//
//    val response = sttpBackend.send(request)
//    response
//  }
}
