package consul4s.ziojson.model

import consul4s.model.{CheckStatus, ServiceKind, SessionBehavior}
import consul4s.model.agent.{AliasCheck, MemberInfo, TaggedAddress, Token, UpstreamDestType, Weights}
import consul4s.model.catalog.{Node, NodeDeregistration}
import consul4s.model.coordinate.NodeCoordinate.Coord
import consul4s.model.event.UserEvent
import consul4s.model.health.HealthCheck.HealthCheckDefinition
import consul4s.model.health.NewHealthCheck.NewHealthCheckDefinition
import consul4s.model.kv.KVPair
import consul4s.model.query.QueryResult.DNS
import consul4s.model.session.SessionId
import consul4s.model.transaction.{CheckOp, KVOp, NodeOp, ServiceOp}
import consul4s.model.transaction.TxResults.TxError
import consul4s.model.transaction.TxTask.NodeDefinition
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait PrimitiveTypes {
  implicit val checkStatusEncoder: JsonEncoder[CheckStatus] = JsonEncoder.string.contramap(_.value)
  implicit val checkStatusDecoder: JsonDecoder[CheckStatus] = JsonDecoder.string.map(v => CheckStatus.withValue(v))

  implicit val serviceKindEncoder: JsonEncoder[ServiceKind] = JsonEncoder.string.contramap(_.value)
  implicit val serviceKindDecoder: JsonDecoder[ServiceKind] = JsonDecoder.string.map(v => ServiceKind.withValue(v))

  implicit val sessionBehaviorEncoder: JsonEncoder[SessionBehavior] = JsonEncoder.string.contramap(_.value)
  implicit val sessionBehaviorDecoder: JsonDecoder[SessionBehavior] = JsonDecoder.string.map(v => SessionBehavior.withValue(v))

  implicit val upstreamDestTypeEncoder: JsonEncoder[UpstreamDestType] = JsonEncoder.string.contramap(_.value)
  implicit val upstreamDestTypeDecoder: JsonDecoder[UpstreamDestType] = JsonDecoder.string.map(v => UpstreamDestType.withValue(v))

  implicit val kvOpEncoder: JsonEncoder[KVOp] = JsonEncoder.string.contramap(_.value)
  implicit val kvOpDecoder: JsonDecoder[KVOp] = JsonDecoder.string.map(v => KVOp.withValue(v))

  implicit val checkOpEncoder: JsonEncoder[CheckOp] = JsonEncoder.string.contramap(_.value)
  implicit val checkOpDecoder: JsonDecoder[CheckOp] = JsonDecoder.string.map(v => CheckOp.withValue(v))

  implicit val nodeOpEncoder: JsonEncoder[NodeOp] = JsonEncoder.string.contramap(_.value)
  implicit val nodeOpDecoder: JsonDecoder[NodeOp] = JsonDecoder.string.map(v => NodeOp.withValue(v))

  implicit val serviceOpEncoder: JsonEncoder[ServiceOp] = JsonEncoder.string.contramap(_.value)
  implicit val serviceOpDecoder: JsonDecoder[ServiceOp] = JsonDecoder.string.map(v => ServiceOp.withValue(v))

  implicit val weightsEncoder: JsonEncoder[Weights] = DeriveJsonEncoder.gen[Weights]
  implicit val weightsDecoder: JsonDecoder[Weights] = DeriveJsonDecoder.gen[Weights]

  implicit val taggedAddressEncoder: JsonEncoder[TaggedAddress] = DeriveJsonEncoder.gen[TaggedAddress]
  implicit val taggedAddressDecoder: JsonDecoder[TaggedAddress] = DeriveJsonDecoder.gen[TaggedAddress]

  implicit val coordEncoder: JsonEncoder[Coord] = DeriveJsonEncoder.gen[Coord]
  implicit val coordDecoder: JsonDecoder[Coord] = DeriveJsonDecoder.gen[Coord]

  implicit val userEventEncoder: JsonEncoder[UserEvent] = DeriveJsonEncoder.gen[UserEvent]
  implicit val userEventDecoder: JsonDecoder[UserEvent] = DeriveJsonDecoder.gen[UserEvent]

  implicit val kvPairEncoder: JsonEncoder[KVPair] = DeriveJsonEncoder.gen[KVPair]
  implicit val kvPairDecoder: JsonDecoder[KVPair] = DeriveJsonDecoder.gen[KVPair]

  implicit val dnsEncoder: JsonEncoder[DNS] = DeriveJsonEncoder.gen[DNS]
  implicit val dnsDecoder: JsonDecoder[DNS] = DeriveJsonDecoder.gen[DNS]

  implicit val sessionIdEncoder: JsonEncoder[SessionId] = DeriveJsonEncoder.gen[SessionId]
  implicit val sessionIdDecoder: JsonDecoder[SessionId] = DeriveJsonDecoder.gen[SessionId]

  implicit val tokenEncoder: JsonEncoder[Token] = DeriveJsonEncoder.gen[Token]
  implicit val tokenDecoder: JsonDecoder[Token] = DeriveJsonDecoder.gen[Token]

  implicit val memberInfoEncoder: JsonEncoder[MemberInfo] = DeriveJsonEncoder.gen[MemberInfo]
  implicit val memberInfoDecoder: JsonDecoder[MemberInfo] = DeriveJsonDecoder.gen[MemberInfo]

  implicit val nodeEncoder: JsonEncoder[Node] = DeriveJsonEncoder.gen[Node]
  implicit val nodeDecoder: JsonDecoder[Node] = DeriveJsonDecoder.gen[Node]

  implicit val nodeDeregistrationEncoder: JsonEncoder[NodeDeregistration] = DeriveJsonEncoder.gen[NodeDeregistration]
  implicit val nodeDeregistrationDecoder: JsonDecoder[NodeDeregistration] = DeriveJsonDecoder.gen[NodeDeregistration]

  implicit val newHealthCheckDefinitionEncoder: JsonEncoder[NewHealthCheckDefinition] = DeriveJsonEncoder.gen[NewHealthCheckDefinition]
  implicit val newHealthCheckDefinitionDecoder: JsonDecoder[NewHealthCheckDefinition] = DeriveJsonDecoder.gen[NewHealthCheckDefinition]

  implicit val healthCheckDefinitionEncoder: JsonEncoder[HealthCheckDefinition] = DeriveJsonEncoder.gen[HealthCheckDefinition]
  implicit val healthCheckDefinitionDecoder: JsonDecoder[HealthCheckDefinition] = DeriveJsonDecoder.gen[HealthCheckDefinition]

  implicit val txErrorEncoder: JsonEncoder[TxError] = DeriveJsonEncoder.gen[TxError]
  implicit val txErrorDecoder: JsonDecoder[TxError] = DeriveJsonDecoder.gen[TxError]

  implicit val nodeDefinitionEncoder: JsonEncoder[NodeDefinition] = DeriveJsonEncoder.gen[NodeDefinition]
  implicit val nodeDefinitionDecoder: JsonDecoder[NodeDefinition] = DeriveJsonDecoder.gen[NodeDefinition]

  implicit val aliasCheckEncoder: JsonEncoder[AliasCheck] = DeriveJsonEncoder.gen[AliasCheck]
  implicit val aliasCheckDecoder: JsonDecoder[AliasCheck] = DeriveJsonDecoder.gen[AliasCheck]
}
