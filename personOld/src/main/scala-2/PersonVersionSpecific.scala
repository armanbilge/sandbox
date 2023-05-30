import upickle.default._

trait PersonVersionSpecific {
  implicit lazy val reader: ReadWriter[Person] = macroRW
}
