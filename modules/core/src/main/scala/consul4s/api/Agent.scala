package consul4s.api

import sttp.client._

trait Agent[F[_]] { this: ConsulApi[F] =>

}
