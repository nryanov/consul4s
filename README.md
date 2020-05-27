# consul4s
 
A native Scala client for interacting with Consul built on top of [sttp-client](https//github.com/softwaremill/sttp).


> STTP: Backend implementations include ones based on [akka-http](https://doc.akka.io/docs/akka-http/current/scala/http/), [async-http-client](https://github.com/AsyncHttpClient/async-http-client), [http4s](https://http4s.org), [OkHttp](http://square.github.io/okhttp/), and HTTP clients which ship with Java. They integrate with [Akka](https://akka.io), [Monix](https://monix.io), [fs2](https://github.com/functional-streams-for-scala/fs2), [cats-effect](https://github.com/typelevel/cats-effect), [scalaz](https://github.com/scalaz/scalaz) and [ZIO](https://github.com/zio/zio). 

## Features
Currently `consul4s` supports these endpoints:
- agent
- catalog
- coordinate
- event
- health
- kv
- prepared queries (only existing query execution)
- session
- status
- transaction

## Getting Started