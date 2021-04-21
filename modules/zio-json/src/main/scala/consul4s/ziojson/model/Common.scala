package consul4s.ziojson.model

import consul4s.model._
import consul4s.model.agent.{TaggedAddress, Weights}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait Common {
  implicit val checkStatusEncoder: JsonEncoder[CheckStatus] = JsonEncoder.string.contramap(_.value)
  implicit val serviceKindEncoder: JsonEncoder[ServiceKind] = JsonEncoder.string.contramap(_.value)
  implicit val sessionBehaviorEncoder: JsonEncoder[SessionBehavior] = JsonEncoder.string.contramap(_.value)
  implicit val taggedAddressEncoder: JsonEncoder[TaggedAddress] = DeriveJsonEncoder.gen[TaggedAddress]
  implicit val weightsEncoder: JsonEncoder[Weights] = DeriveJsonEncoder.gen[Weights]

  implicit val checkStatusDecoder: JsonDecoder[CheckStatus] = JsonDecoder.string.map(v => CheckStatus.withValue(v))
  implicit val serviceKindDecoder: JsonDecoder[ServiceKind] = JsonDecoder.string.map(v => ServiceKind.withValue(v))
  implicit val sessionBehaviorDecoder: JsonDecoder[SessionBehavior] = JsonDecoder.string.map(v => SessionBehavior.withValue(v))
  implicit val taggedAddressDecoder: JsonDecoder[TaggedAddress] = DeriveJsonDecoder.gen[TaggedAddress]
  implicit val weightsDecoder: JsonDecoder[Weights] = DeriveJsonDecoder.gen[Weights]
}
