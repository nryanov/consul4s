package consul4s

import com.dimafeng.testcontainers.GenericContainer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy

class ConsulContainer(underlying: GenericContainer) extends GenericContainer(underlying) {}

object ConsulContainer {
  val waitStrategy = new HttpWaitStrategy().forPort(8500)

  case class Def()
      extends GenericContainer.Def[ConsulContainer](
        new ConsulContainer(GenericContainer(dockerImage = "consul:1.9.3", exposedPorts = Seq(8500), waitStrategy = waitStrategy))
      )
}
