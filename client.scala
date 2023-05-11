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
import com.comcast.ip4s.*

object Client extends UringApp.Simple:
  def run =
    UringSocketGroup[IO].client(SocketAddress.fromString("localhost:8080").get).use { client =>
      IO.println("connected") *>
        client.write(Chunk.constant(0.toByte, 1024)).foreverM
    }
