package consul4s.zio.json.model

import consul4s.model._
import consul4s.model.agent.{TaggedAddress, Weights}
import consul4s.zio.json.macros.ConverterMacros
import zio.json.JsonCodec

trait Common {
  implicit val checkStatusCodec: JsonCodec[CheckStatus] = JsonCodec.string.xmap(v => CheckStatus.withValue(v), _.value)
  implicit val serviceKindCodec: JsonCodec[ServiceKind] = JsonCodec.string.xmap(v => ServiceKind.withValue(v), _.value)
  implicit val sessionBehaviorCodec: JsonCodec[SessionBehavior] = JsonCodec.string.xmap(v => SessionBehavior.withValue(v), _.value)

  private[zio] case class WeightsRepr(
    Passing: Int,
    Warning: Int
  )

  implicit val weightsCodec: JsonCodec[Weights] = ConverterMacros.derive[WeightsRepr, Weights]

  private[zio] case class TaggedAddressRepr(
    Address: String,
    Port: Int
  )

  implicit val taggedAddressCodec: JsonCodec[TaggedAddress] = ConverterMacros.derive[TaggedAddressRepr, TaggedAddress]
}
