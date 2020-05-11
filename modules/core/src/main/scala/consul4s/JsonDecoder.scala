package consul4s

import sttp.client._
import consul4s.model.{KeyValue, MemberInfo, NodeCheck, NodeForService, NodeInfo, ServiceCheck, ServiceInfo}

trait JsonDecoder {
  def asBooleanUnsafe: ResponseAs[Boolean, Nothing]

  def asKeyValuesOption: ResponseAs[Option[List[KeyValue]], Nothing]

  def asNodeChecksOption: ResponseAs[Option[List[NodeCheck]], Nothing]

  def asServiceChecksOption: ResponseAs[Option[List[ServiceCheck]], Nothing]

  def asNodeChecksUnsafe: ResponseAs[List[NodeCheck], Nothing]

  def asServiceChecksUnsafe: ResponseAs[List[ServiceCheck], Nothing]

  def asStringUnsafe: ResponseAs[String, Nothing]

  def asStringListUnsafe: ResponseAs[List[String], Nothing]

  def asStringOption: ResponseAs[Option[String], Nothing]

  def asStringListOption: ResponseAs[Option[List[String]], Nothing]

  def asNodeInfosOption: ResponseAs[Option[List[NodeInfo]], Nothing]

  def asNodeInfosUnsafe: ResponseAs[List[NodeInfo], Nothing]

  def asMapOption: ResponseAs[Option[Map[String, String]], Nothing]

  def asMapUnsafe: ResponseAs[Map[String, String], Nothing]

  def asMapMultipleValuesOption: ResponseAs[Option[Map[String, List[String]]], Nothing]

  def asMapMultipleValuesUnsafe: ResponseAs[Map[String, List[String]], Nothing]

  def asServicesInfoOption: ResponseAs[Option[List[ServiceInfo]], Nothing]

  def asServicesInfoUnsafe: ResponseAs[List[ServiceInfo], Nothing]

  def asNodesForServiceOption: ResponseAs[Option[List[NodeForService]], Nothing]

  def asNodesForServiceUnsafe: ResponseAs[List[NodeForService], Nothing]

  def asMembersInfoOption: ResponseAs[Option[List[MemberInfo]], Nothing]

  def asMembersInfoUnsafe: ResponseAs[List[MemberInfo], Nothing]
}
