package consul4s.ziojson.model

import consul4s.model._
import consul4s.model.agent.{TaggedAddress, Weights}
import zio.json.{DeriveJsonCodec, JsonCodec}

trait Common {
  implicit val checkStatusCodec: JsonCodec[CheckStatus] = JsonCodec.string.xmap(v => CheckStatus.withValue(v), _.value)
  implicit val serviceKindCodec: JsonCodec[ServiceKind] = JsonCodec.string.xmap(v => ServiceKind.withValue(v), _.value)
  implicit val sessionBehaviorCodec: JsonCodec[SessionBehavior] = JsonCodec.string.xmap(v => SessionBehavior.withValue(v), _.value)
  implicit val weightsCodec: JsonCodec[Weights] = DeriveJsonCodec.gen[Weights]
  implicit val taggedAddressCodec: JsonCodec[TaggedAddress] = DeriveJsonCodec.gen[TaggedAddress]
}
