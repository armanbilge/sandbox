trait Foo {
  lazy val sameSame: AnyRef = new AnyRef

  def main(args: Array[String]): Unit = println(sameSame eq sameSame)
}
