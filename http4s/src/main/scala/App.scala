package hello.world

import cats.effect._
import org.http4s.Headers
import org.http4s.syntax.all._
import org.http4s.ember.client.EmberClientBuilder

object App extends IOApp.Simple {
  def run = EmberClientBuilder.default[IO].withHttp2.build
    .map(TestService.fromClient(_, uri"http://localhost:8123/"))
    .use { client =>
      client.noStreaming(TestMessage("foo", 42, None), Headers.empty).debug().void
    }
}
