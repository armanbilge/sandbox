//> using scala "3.3.0"
//> using lib "org.scalameta::munit::1.0.0-M8"

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class NativeSpec extends munit.FunSuite {
  test("repro") {
    Future(2).map(res => assertEquals(1 + 1, res))
  }
}
