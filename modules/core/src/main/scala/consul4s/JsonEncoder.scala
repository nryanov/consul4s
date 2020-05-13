package consul4s

import consul4s.model.catalog.{CatalogDeregistration, CatalogRegistration}

trait JsonEncoder {
  def catalogRegistrationToJson(catalogRegistration: CatalogRegistration): String

  def catalogDeregistrationToJson(catalogDeregistration: CatalogDeregistration): String

  def membersOptsToJson(): String
}
