package consul4s

import org.scalatest.concurrent.Eventually
import org.scalatest.{Assertion, EitherValues}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import sttp.client.ResponseError

import scala.concurrent.duration._

trait BaseSpec extends AnyWordSpec with Matchers with EitherValues with Eventually {
  def runEither(fa: => Either[ResponseError[Exception], Assertion]): Assertion =
    fa.fold(
      error => fail(error),
      _ => succeed
    )

  def runEitherEventually(fa: => Either[ResponseError[Exception], Assertion]): Assertion =
    eventually(timeout(5000 millisecond), interval(100 millisecond))(runEither(fa))
}
