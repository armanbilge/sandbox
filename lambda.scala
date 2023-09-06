//> using dep "org.typelevel::feral-lambda::0.2.3"

import cats.effect._
import feral.lambda._

class MyLambda extends IOLambda[Unit, Unit] {

  def handler = for {
    _ <- IO.println("trying to log something").toResource
  } yield { (env: LambdaEnv[IO, Unit]) =>
    IO.println("trying to log something else") *> IO.unit.map(Some(_))
  }

}
