package consul4s.example

import zio._
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import consul4s.circe._
import consul4s.v1.ConsulClient

object ZioBackendExample extends ZIOAppDefault {
  override def run = for {
    client <- createClient()
    result <- client.getDatacenters()
    body <- ZIO.fromEither(result.body)
    _ <- Console.printLine(s"Datacenters: ${body.mkString("[", ", ", "]")}")
  } yield ()

  def createClient() =
    AsyncHttpClientZioBackend().map(ConsulClient[Task])
}
