package consul4s

import consul4s.model.agent.{Check, CheckUpdate, NewService, Token}
import consul4s.model.catalog.{NodeDeregistration, NodeRegistration}
import consul4s.model.coordinate.NodeCoordinate
import consul4s.model.session.SessionInfo
import consul4s.model.transaction.TxTask

trait JsonEncoder {
  def nodeRegistrationToJson(value: NodeRegistration): String

  def nodeDeregistrationToJson(value: NodeDeregistration): String

  def sessionToJson(value: SessionInfo): String

  def checkToJson(check: Check): String

  def checkUpdateToJson(checkUpdate: CheckUpdate): String

  def newServiceToJson(service: NewService): String

  def tokenAsJson(token: Token): String

  def nodeCoordinateToJson(nodeCoordinate: NodeCoordinate): String

  def txTasksToJson(txTasks: List[TxTask]): String
}
