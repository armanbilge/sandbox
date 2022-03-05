package sandbox

object MyScalaClass extends ClassValue[String] {
  def computeValue(x: Class[?]) = ???
}
