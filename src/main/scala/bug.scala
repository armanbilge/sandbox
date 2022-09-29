package nowhere

trait Foo { self: Baz.type =>

  private final class Bar[A](whatever: Unit) extends AbstractBar[A]

}

object Baz extends Foo {
  abstract class AbstractBar[A]
}
