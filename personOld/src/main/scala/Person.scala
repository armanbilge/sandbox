case class Person private (name: String) {
  def withName(name: String): Person = copy(name = name)

  private def copy(name: String = this.name): Person =
    new Person(name)
}

object Person {
  def apply(name: String): Person = new Person(name)
  private def unapply(person: Person): Some[Person] = Some(person)
  def fromProduct(p: Product): Person = p.productArity match {
    case 1 => Person(p.productElement(0).asInstanceOf[String])
  }
}
