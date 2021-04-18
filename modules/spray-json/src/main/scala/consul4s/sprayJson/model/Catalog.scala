package consul4s.sprayJson.model

import consul4s.model.catalog.NodeServiceList.NodeServiceListInternal
import consul4s.model.catalog._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait Catalog extends DefaultJsonProtocol { this: Health with Agent with Common =>
  implicit val catalogServiceFormat: RootJsonFormat[CatalogService] = jsonFormat16(CatalogService.apply)

  implicit val newCatalogServiceFormat: RootJsonFormat[NewCatalogService] = jsonFormat9(NewCatalogService.apply)

  implicit val nodeFormat: RootJsonFormat[Node] = jsonFormat6(Node.apply)

  implicit val nodeDeregistrationFormat: RootJsonFormat[NodeDeregistration] = jsonFormat4(NodeDeregistration.apply)

  implicit val nodeRegistrationFormat: RootJsonFormat[NodeRegistration] = rootFormat(lazyFormat(jsonFormat10(NodeRegistration.apply)))

  implicit val nodeServiceListFormat: RootJsonFormat[NodeServiceListInternal] = jsonFormat2(NodeServiceListInternal.apply)

  implicit val nodeServiceMapFormat: RootJsonFormat[NodeServiceMap] = jsonFormat2(NodeServiceMap.apply)

}
