//> using dep org.http4s::http4s-ember-server::0.23.24
//> using dep org.http4s::http4s-ember-client::0.23.24

import cats.data.*
import cats.effect.*
import com.comcast.ip4s.*
import fs2.Stream
import fs2.io.net.Network
import org.http4s.*
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.core.h2.H2Keys.Http2PriorKnowledge
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.server.staticcontent.*
import java.nio.file.Paths
import scala.concurrent.duration.*

object Backend extends IOApp.Simple:

  def run = EmberClientBuilder.default[IO].withHttp2.build.use { client =>
    internalServer.use { internal =>
      FrontendServer(client, internal.baseUri).build.use { server =>
        IO.println(s"Navigate to ${server.baseUri}") *> IO.never
      }
    }
  }

  class FrontendServer(client: Client[IO], internalServiceUri: Uri) {

    def build = Network[IO].tlsContext
      .fromKeyStoreFile(
        Paths.get("keystore.jks"),
        "password".toCharArray,
        "password".toCharArray
      )
      .toResource
      .flatMap { tls =>
        EmberServerBuilder
          .default[IO]
          .withHttp2
          .withTLS(tls)
          .withShutdownTimeout(1.second)
          .withHttpApp(service.orNotFound)
          .build
      }

    private def service = Router("/api" -> api, "/" -> files)

    private def files = fileService[IO](FileService.Config("."))

    private def api =
      HttpRoutes[IO] { request =>
        OptionT.liftF {
          IO.println(
            s"frontend service received ${request.httpVersion} request"
          ) *>
            IO.println("frontend service calling upstream internal service") *>
            client
              .expect[Unit](
                Request(uri = internalServiceUri)
                  .withAttribute(Http2PriorKnowledge, ())
              )
              .onCancel(
                IO.println("frontend service handler canceled")
              )
              .as(Response())
        }
      }
  }

  def internalServer =
    EmberServerBuilder
      .default[IO]
      .withPort(port"0")
      .withHttp2
      .withShutdownTimeout(1.second)
      .withHttpApp(internalService.orNotFound)
      .build

  def internalService = HttpRoutes[IO] { request =>
    OptionT.liftF {
      IO.println(s"internal service received ${request.httpVersion} request")
        .as(
          Response()
            .withEntity(Stream.never[IO].covaryOutput[Byte].onFinalizeCase {
              case Resource.ExitCase.Canceled =>
                IO.println("internal service handler canceled")
              case _ => IO.unit
            })
        )
    }
  }
