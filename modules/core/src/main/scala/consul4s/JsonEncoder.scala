package consul4s

import consul4s.model.agent.{Check, CheckUpdate, Service}
import consul4s.model.catalog.{EntityDeregistration, EntityRegistration}
import consul4s.model.session.SessionInfo

trait JsonEncoder {
  def entityRegistrationToJson(value: EntityRegistration): String

  def entityDeregistrationToJson(value: EntityDeregistration): String

  def sessionToJson(value: SessionInfo): String

  def checkToJson(check: Check): String

  def checkUpdateToJson(checkUpdate: CheckUpdate): String

  def serviceToJson(service: Service): String
}
