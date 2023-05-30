import pureconfig._
import upickle.default._

object App extends AppVersionSpecific {

  def main(args: Array[String]): Unit = {
    val string = """{ "name": "foo" }"""
    println(ConfigSource.string(string).load[Person])
    println(read[Person](string))
  }
}
