package consul4s.model.agent

final case class Weights(Passing: Int, Warning: Int)

object Weights {
  val DEFAULT = Weights(1, 1)
}
