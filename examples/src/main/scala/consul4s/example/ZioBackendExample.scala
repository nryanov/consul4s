package consul4s.example

import consul4s.circe._
import consul4s.v1.ConsulClient
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.console._

object ZioBackendExample extends zio.App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = createClient().use { client =>
    for {
      result <- client.getDatacenters()
      body <- ZIO.fromEither(result.body)
      _ <- putStrLn(s"Datacenters: ${body.mkString("[", ", ", "]")}")
    } yield ()
  }.exitCode

  def createClient(): ZManaged[Any, Throwable, ConsulClient[Task]] =
    AsyncHttpClientZioBackend.managed().map(ConsulClient[Task])
}
