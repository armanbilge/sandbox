//> using lib "com.armanbilge::fs2-io_uring::0.1.0"
//> using platform "native"
// brew install liburing
//> using nativeCompile "-I/home/linuxbrew/.linuxbrew/include"
//> using nativeLinking "/home/linuxbrew/.linuxbrew/lib/liburing.a"

import cats.effect.*
import cats.syntax.all.*
import fs2.*
import fs2.io.uring.*
import fs2.io.uring.net.*

object App extends UringApp:
  def run(args: List[String]) =
    UringSocketGroup[IO].serverResource().use { (address, clients) =>
      val echoServer = clients.map { c =>
        Stream.exec(IO.println("client accepted")) ++
          c.reads.through(c.writes)
      }.parJoinUnbounded
      echoServer.compile.drain.background.surround {
        UringSocketGroup[IO]
          .client(address, Nil)
          .use { client =>
            IO.println("client connected") *>
              client
                .write(Chunk.constant(0.toByte, 1024))
                .foreverM
                .background
                .surround {
                  client.reads.compile.drain
                }
          }
          .parReplicateA_(args.head.toInt)
      }
    } as ExitCode.Success
