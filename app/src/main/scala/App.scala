import pureconfig._

object App extends AppVersionSpecific {
  def main(args: Array[String]): Unit = {
    println(ConfigSource.string("{ name = foo, age = 1 }").load[Person])
  }
}
