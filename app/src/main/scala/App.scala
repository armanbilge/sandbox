import pureconfig._
import pureconfig.generic.derivation.default._

object App {
  implicit def personReader: ConfigReader[Person] = ConfigReader.derived[Person]

  def main(args: Array[String]): Unit = {
    println(ConfigSource.string("{ name = foo }").load[Person])
  }
}
