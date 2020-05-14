package consul4s

import consul4s.model.catalog.{EntityDeregistration, EntityRegistration}
import consul4s.model.session.SessionInfo

trait JsonEncoder {
  def entityRegistrationToJson(value: EntityRegistration): String

  def entityDeregistrationToJson(value: EntityDeregistration): String

  def sessionToJson(value: SessionInfo): String
}
