//> using scala "2.12.16"
import scala.io.Source

object App {

  def main(args: Array[String]): Unit = {}

  /** Yield a generator of (start, end, fields) for the given Unicode data file.
    * These files are of the same basic format: a semicolon-delimited set of
    * columns, where the first column is either a single element or a range of
    * characters. In this case, the range implied by start and end are
    * inclusive.
    */
  def parseUnicodeDataFile(file: Source) = {
    file.getLines().flatMap { l =>
      l.split('#').headOption.map(_.trim()).filterNot(_.isEmpty()).map { l =>
        val parts = l.split(';').map(_.strip())
        parts(0).split("\\.\\.") match {
          case Array(x) =>
            val start = Integer.parseInt(x, 16)
            (start, start, parts.tail)
          case Array(x, y) =>
            val start = Integer.parseInt(x, 16)
            val end = Integer.parseInt(y, 16)
            (start, end, parts.tail)
        }
      }
    }
  }

  class MappedValue(flags: Int, rule: String, chars: String) {

    def buildInt: Int = {
      val status = rule match {
        case "disallowed" => 0
        case "ignored" => 1 // We're mapping to a string of length 0
        case "mapped" => 1
        case "deviation" => 2
        case "valid" => 3
      }

      // check all the bits
      assert(flags < (1 << 2))
      assert(index < (1 << 16))

      flags << 23 | status << 21 | index << 5 | numchars
    }

  }

  object MappedValue {
    def apply(parts: Array[String]): MappedValue = {
      var flags = 0
      var rule = parts(0)

      // If there are two parts, the second part is the mapping in question.
      val chars = if (parts.length > 1 && parts(1).nonEmpty) {
        val cps = parts(1).split(' ').map(Integer.parseInt(_, 16))
        new String(cps, 0, cps.length)
      } else ""

      // In the case of disallowed_STD3_*, we process the real rule as the
      // text following the last _, and set a flag noting to note the
      // difference.
      if (rule.startsWith("disallowed_STD3")) {
        flags |= 1
        rule = rule.split("_").last
      }

      MappedValue(flags, rule, chars)
    }
  }

}
