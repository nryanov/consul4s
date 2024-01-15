package consul4s.example

import consul4s.circe._
import consul4s.v1.ConsulClient
import cats.effect._
import cats.data.EitherT
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

object CatsBackendExample extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = createClient[IO]().use { client =>
    for {
      response <- client.getDatacenters()
      body = response.body
      _ <- EitherT.fromEither[IO](body).semiflatMap(result => IO.delay(println(result))).value
    } yield ExitCode.Success
  }

  def createClient[F[_]: Concurrent: Async](): Resource[F, ConsulClient[F]] =
    AsyncHttpClientCatsBackend.resource().map(client => ConsulClient(client))
}
