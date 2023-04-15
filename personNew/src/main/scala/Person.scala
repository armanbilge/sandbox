case class Person private (name: String, age: Int) {
  def this(name: String) = this(name, -1)

  def withName(name: String): Person = copy(name = name)
  def withAge(age: Int): Person = copy(age = age)

  private def copy(name: String = this.name, age: Int = this.age): Person =
    new Person(name, age)
}

object Person {
  def apply(name: String, age: Int): Person = new Person(name, age)

  def apply(name: String): Person = new Person(name)

  private def unapply(person: Person): Some[Person] = Some(person)
}
