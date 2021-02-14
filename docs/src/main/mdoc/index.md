---
layout: home
title:  "Home"
section: "home"
---

[![GitHub license](https://img.shields.io/github/license/nryanov/consul4s)](https://github.com/nryanov/consul4s/blob/master/LICENSE.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nryanov.consul4s/consul4s-core_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nryanov.consul4s/consul4s-core_2.13)

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
consul4s is published for Scala 2.13 and 2.12 to Maven Central, so just add the following to your build.sbt:

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

```scala mdoc
import consul4s.v1._
import consul4s.circe._
import sttp.client.HttpURLConnectionBackend

object Main {
 def main(args: Array[String]): Unit = {
    val backend = HttpURLConnectionBackend()
    val client = ConsulClient(backend) // will use default host and port: http://localhost:8500

    val datacenters = for {
      datacenters <- client.getDatacenters().body
    } yield datacenters
   
   println(datacenters)
  }
}
```
