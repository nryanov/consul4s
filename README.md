# consul4s
 
A native Scala client for interacting with Consul built on top of [sttp-client](https://github.com/softwaremill/sttp). 

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

## Modules
Consul4s uses multi-project structure and contains of the following modules_:

* [`consul4s-core`](modules/core) - core classes/functions
* [`consul4s-circe`](modules/circe) - circe integration
* [`consul4s-json4s`](modules/json4s) - json4s integration
* [`consul4s-spray-json`](modules/spray-json) - spray-json integration

## Installation
```sbt
libraryDependencies ++= Seq(
  "com.nryanov.consul4s" %% "consul4s-core" % "[version]",
  // And one of the following:
  "com.nryanov.consul4s" %% "consul4s-circe" % "[version]",
  "com.nryanov.consul4s" %% "consul4s-json4s" % "[version]",
  "com.nryanov.consul4s" %% "consul4s-spray-json" % "[version]",
)
```

Also you can add any sttp-client backend you want. 

## Getting Started
Without any additional dependencies you can use the default `HttpURLConnectionBackend` as backend for requests. But sttp also supports other backend implementations:
> STTP: Backend implementations include ones based on [akka-http](https://doc.akka.io/docs/akka-http/current/scala/http/), [async-http-client](https://github.com/AsyncHttpClient/async-http-client), [http4s](https://http4s.org), [OkHttp](http://square.github.io/okhttp/), and HTTP clients which ship with Java. They integrate with [Akka](https://akka.io), [Monix](https://monix.io), [fs2](https://github.com/functional-streams-for-scala/fs2), [cats-effect](https://github.com/typelevel/cats-effect), [scalaz](https://github.com/scalaz/scalaz) and [ZIO](https://github.com/zio/zio).

```scala
import consul4s.v1._
import consul4s.circe._
import sttp.client.HttpURLConnectionBackend

object Main {
 def main(args: Array[String]): Unit = {
    val backend = HttpURLConnectionBackend()
    val client = ConsulClient(backend) // will use default host and port: http://localhost:8500

    for {
      datacenters <- client.getDatacenters().body
    } yield datacenters
  }
}
```