//> using dep "org.typelevel::feral-lambda-http4s::0.2.3"
//> using dep "org.http4s::http4s-ember-client::0.23.22"
//> using dep "org.http4s::http4s-dsl::0.23.22"
//> using dep "org.tpolecat::natchez-xray::0.3.3"
//> using dep "org.tpolecat::natchez-http4s::0.5.0"

import cats.effect._
import cats.effect.std.Random
import feral.lambda._
import feral.lambda.events._
import feral.lambda.http4s._
import natchez.Trace
import natchez.http4s.NatchezMiddleware
import natchez.xray.XRay
import org.http4s.HttpRoutes
import org.http4s.client.Client
import org.http4s.dsl.Http4sDsl
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.syntax.all._

/**
 * For a gentle introduction, please look at the `KinesisLambda` first which uses
 * `IOLambda.Simple`.
 *
 * The `IOLambda` uses a slightly more complicated encoding by introducing an effect
 * `LambdaEnv[F]` which provides access to the event and context in `F`. This allows you to
 * compose your handler as a stack of "middlewares", making it easy to e.g. add tracing to your
 * Lambda.
 */
class Http4sHandler
    extends IOLambda[ApiGatewayProxyEventV2, ApiGatewayProxyStructuredResultV2] {

  /**
   * Actually, this is a `Resource` that builds your handler. The handler is acquired exactly
   * once when your Lambda starts and is permanently installed to process all incoming events.
   *
   * The handler itself is a program expressed as `IO[Option[Result]]`, which is run every time
   * that your Lambda is triggered. This may seem counter-intuitive at first: where does the
   * event come from? Because accessing the event via `LambdaEnv` is now also an effect in `IO`,
   * it becomes a step in your program.
   */
  def handler = for {
    entrypoint <- Resource
      .eval(Random.scalaUtilRandom[IO])
      .flatMap(implicit r => XRay.entryPoint[IO]())
    client <- EmberClientBuilder.default[IO].build
  } yield { implicit env => // the LambdaEnv provides access to the event and context

    // a middleware to add tracing to any handler
    // it extracts the kernel from the event and adds tags derived from the context
    TracedHandler(entrypoint) { implicit trace =>
      val tracedClient = NatchezMiddleware.client(client)

      // a "middleware" that converts an HttpRoutes into a ApiGatewayProxyHandler
      ApiGatewayProxyHandler(myRoutes[IO](tracedClient))
    }
  }

  /**
   * Nothing special about this method, including its existence, just an example :)
   */
  def myRoutes[F[_]: Concurrent: Trace](client: Client[F]): HttpRoutes[F] = {
    implicit val dsl = Http4sDsl[F]
    import dsl._

    val routes = HttpRoutes.of[F] {
      case GET -> Root / "foo" => Ok("bar")
      case GET -> Root / "joke" => Ok(client.expect[String](uri"icanhazdadjoke.com"))
    }

    NatchezMiddleware.server(routes)
  }

}
