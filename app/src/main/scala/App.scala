import cats.syntax.all._
import PersonSemigroup._

object App {
  def main(args: Array[String]): Unit = {
    println(Person("foo", 2) |+| Person("bar", 3))
  }
}
