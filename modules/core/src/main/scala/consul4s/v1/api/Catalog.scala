package consul4s.v1.api

import consul4s.model.catalog._
import sttp.client._

trait Catalog[F[_]] { this: ConsulApi[F] =>
  // PUT /catalog/register
  def registerEntity(value: NodeRegistration): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/register")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.nodeRegistrationToJson(value))

    val response = sttpBackend.send(request)
    response
  }

  // PUT /catalog/deregister
  // CatalogDeregistration: namespace > ns
  def deregisterEntity(value: NodeDeregistration): F[Result[Unit]] = {
    val requestTemplate = basicRequest.put(uri"$url/catalog/deregister")
    val request = requestTemplate.copy(response = asResultUnit).body(jsonEncoder.nodeDeregistrationToJson(value))

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/datacenters
  def datacenters(): F[Result[List[String]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/datacenters")
    val request = requestTemplate.copy(response = jsonDecoder.asStringList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/nodes
  def nodes(
    dc: Option[String] = None,
    near: Option[String] = None,
    nodeMeta: Option[String] = None,
    filter: Option[String] = None
  ): F[Result[List[Node]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/nodes?dc=$dc&near=$near&node-meta=$nodeMeta&filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/services
  def services(
    dc: Option[String] = None,
    nodeMeta: Option[String] = None
  ): F[Result[Map[String, List[String]]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/services?dc=$dc&node-meat=$nodeMeta")
    val request = requestTemplate.copy(response = jsonDecoder.asMapMultipleValues)

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
  ): F[Result[List[CatalogService]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/service/$service?dc=$dc&tag=$tag&near=$near&node-meta=$nodeMeta&filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asCatalogServiceList)

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
  ): F[Result[List[CatalogService]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/connect/$service?dc=$dc&tag=$tag&near=$near&node-meta=$nodeMeta&filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asCatalogServiceList)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/node/:node
  def mapOfServicesForNode(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None
  ): F[Result[Option[NodeServiceMap]]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/node/$node?dc=$dc&filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeServiceMap)

    val response = sttpBackend.send(request)
    response
  }

  // GET	/catalog/node-services/:node
  def listOfServicesForNode(
    node: String,
    dc: Option[String] = None,
    filter: Option[String] = None
  ): F[Result[NodeServiceList]] = {
    val requestTemplate = basicRequest.get(uri"$url/catalog/node-services/$node?dc=$dc&filter=$filter")
    val request = requestTemplate.copy(response = jsonDecoder.asNodeServiceList)

    val response = sttpBackend.send(request)
    response
  }
}
