package hello.world

import cats.effect._
import fs2.Stream
import io.grpc.Metadata
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import fs2.grpc.syntax.all._

object App extends IOApp.Simple {
  val helloService = 
    TestServiceFs2Grpc.bindServiceResource[IO](new TestServiceFs2Grpc {
      def noStreaming(request: TestMessage, ctx: Metadata): IO[TestMessage] =
        IO(request)

      def clientStreaming(request: Stream[IO, TestMessage], ctx: Metadata): IO[TestMessage] =
        request.compile.lastOrError

      def serverStreaming(request: TestMessage, ctx: Metadata): Stream[IO, TestMessage] =
        Stream.emit(request)

      def bothStreaming(request: Stream[IO, TestMessage], ctx: Metadata): Stream[IO, TestMessage] =
        request
    })

  def run = helloService.use { service =>
    NettyServerBuilder
    .forPort(8123)
    .addService(service)
    .resource[IO]
    .evalMap(server => IO(server.start()))
    .useForever
  }
}
