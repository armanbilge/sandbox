//> using platform js

import scala.scalajs.js
import js.annotation._

@JSGlobal
@js.native
class Quill(target: String, options: QuillOptions) extends js.Object

trait QuillOptions extends js.Object:
  var modules: js.UndefOr[js.Dictionary[String]] = js.undefined
  var theme: js.UndefOr[String] = js.undefined

@main def main =
  new Quill("#editor", new QuillOptions {
    modules = js.Dictionary("toolbar" -> "#toolbar")
    theme = "snow"
  })