package consul4s

import org.scalatest.concurrent.Eventually
import org.scalatest.{Assertion, EitherValues}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._

trait BaseSpec extends AnyWordSpec with Matchers with EitherValues with Eventually {
  def runEither(fa: => Either[ConsulResponseError, Assertion]): Assertion =
    fa.fold(
      error => fail(error),
      _ => succeed
    )

  def runEitherEventually(fa: => Either[ConsulResponseError, Assertion]): Assertion =
    eventually(timeout(10 seconds), interval(500 milliseconds))(runEither(fa))
}
