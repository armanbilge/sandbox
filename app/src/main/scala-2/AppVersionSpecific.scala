import pureconfig._
import pureconfig.generic.semiauto._

trait AppVersionSpecific {
  implicit def personReader: ConfigReader[Person] =
    deriveReader
}
