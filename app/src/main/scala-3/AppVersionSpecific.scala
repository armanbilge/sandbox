import pureconfig._
import pureconfig.generic.derivation.default._

trait AppVersionSpecific {
  implicit def personReader: ConfigReader[Person] =
    ConfigReader.derived[Person]
}
