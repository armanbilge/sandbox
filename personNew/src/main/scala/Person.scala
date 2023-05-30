case class Person private[Person] (name: String, age: Int = -1) {
  private[Person] def this(name: String) = this(name, -1)

  def withName(name: String): Person = copy(name = name)
  def withAge(age: Int): Person = copy(age = age)

  private def copy(name: String = this.name, age: Int = this.age): Person =
    new Person(name, age)
}

object Person extends PersonVersionSpecific {
  // causes bug in uPickle's Scala 2 derivation macro, need to comment both `apply`
  def apply(name: String, age: Int = -1): Person = new Person(name, age)
  def apply(name: String): Person = new Person(name)

  private def unapply(person: Person): Some[Person] = Some(person)

  def fromProduct(p: Product): Person =
    p.productArity match {
      case 1 =>
        println("XXX new, case 1")
        Person(
          p.productElement(0).asInstanceOf[String]
        )
      case 2 =>
        println("XXX new, case 2")
        Person(
          p.productElement(0).asInstanceOf[String],
          p.productElement(1).asInstanceOf[Int]
        )
    }
}
