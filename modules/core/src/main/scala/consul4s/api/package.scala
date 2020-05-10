package consul4s

package object api {
  trait ConsulApi[F[_]] extends KVStore[F] with Status[F] with Health[F]
}
