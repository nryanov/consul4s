package consul4s.model.agent

final case class Weights(passing: Int, warning: Int)

object Weights {
  val DEFAULT = Weights(1, 1)
}
