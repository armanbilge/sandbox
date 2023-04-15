import cats._
import cats.derived._

object PersonSemigroup {
  implicit def instance: Semigroup[Person] = semiauto.semigroup
}
