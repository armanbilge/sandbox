object Main {
  def main(args: Array[String]): Unit =
    try {
      throw new Throwable
    } catch {
      case _: Throwable => ()
    }
}