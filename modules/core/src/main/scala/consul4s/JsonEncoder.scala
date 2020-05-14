package consul4s

import consul4s.model.catalog.{EntityDeregistration, EntityRegistration}

trait JsonEncoder {
  def entityRegistrationToJson(value: EntityRegistration): String

  def entityDeregistrationToJson(value: EntityDeregistration): String
}
