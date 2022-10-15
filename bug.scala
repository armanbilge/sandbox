//> using scala "3.2.0"
//> using lib "org.http4s::http4s-ember-server::0.23.16"
//> using lib "org.http4s::http4s-ember-client::0.23.16"
//> using lib "org.http4s::http4s-blaze-server::0.23.12"
//> using lib "org.http4s::http4s-jdk-http-client::0.7.0"

import cats.effect.*
import org.http4s.*
import org.http4s.ember.server.*
import org.http4s.ember.client.*
import org.http4s.server.middleware.{Logger => ServerLogger}
import org.http4s.client.middleware.{Logger => ClientLogger}
import org.http4s.syntax.all.*
import org.http4s.blaze.server.*
import org.http4s.jdkhttpclient.*

object Bug extends IOApp.Simple:

  def routes = HttpRoutes.of[IO] { case _ =>
    IO(Response[IO]().withEntity("Hello"))
  }

  def app = ServerLogger(
    true,
    true,
    cats.arrow.FunctionK.id[IO],
    logAction = Some((x: String) => IO.println(s"server: $x"))
  )(routes.orNotFound)

  def emberServer =
    EmberServerBuilder.default[IO].withHttpApp(app).build

  def blazeServer =
    BlazeServerBuilder[IO].withHttpApp(app).resource

  def emberClient = EmberClientBuilder
    .default[IO]
    .build

  def jdkClient = JdkHttpClient.simple[IO]

  def client = emberClient
    .map(
      ClientLogger(
        true,
        true,
        logAction = Some((x: String) => IO.println(s"client: $x"))
      )(_)
    )

  def run =
    blazeServer.surround {
      client.use { client =>
        client
          .run(
            Request[IO](method = Method.POST, uri = uri"http://localhost:8080")
          )
          .use(_.as[String])
          .replicateA(2)
          .flatMap(IO.println)
      }
    }
