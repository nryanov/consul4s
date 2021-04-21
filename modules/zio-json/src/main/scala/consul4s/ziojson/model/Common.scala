package consul4s.ziojson.model

import consul4s.model._
import consul4s.model.agent.{TaggedAddress, Weights}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}

trait Common {
  implicit val checkStatusCodec: JsonCodec[CheckStatus] = JsonCodec.string.xmap(v => CheckStatus.withValue(v), _.value)
  implicit val serviceKindCodec: JsonCodec[ServiceKind] = JsonCodec.string.xmap(v => ServiceKind.withValue(v), _.value)
  implicit val sessionBehaviorCodec: JsonCodec[SessionBehavior] = JsonCodec.string.xmap(v => SessionBehavior.withValue(v), _.value)

  implicit val weightsEncoder: JsonEncoder[Weights] = DeriveJsonEncoder.gen[Weights]
  implicit val taggedAddressEncoder: JsonEncoder[TaggedAddress] = DeriveJsonEncoder.gen[TaggedAddress]

  implicit val weightsDecoder: JsonDecoder[Weights] = DeriveJsonDecoder.gen[Weights]
  implicit val taggedAddressDecoder: JsonDecoder[TaggedAddress] = DeriveJsonDecoder.gen[TaggedAddress]
}
