scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.5.2"
)

enablePlugins(ScalaUnidocPlugin)

unidoc / sources := {
  (Compile / dependencyClasspath).value.flatMap { attr =>
    import scala.collection.JavaConverters._
    val fs = java.nio.file.FileSystems
      .newFileSystem(attr.data.toPath, null: ClassLoader)
    val matcher = fs.getPathMatcher("glob:*.tasty")
    fs.getRootDirectories()
      .asScala
      .flatMap { root =>
        java.nio.file.Files
          .find(root, Int.MaxValue, (p, _) => p.toString.endsWith(".tasty"))
          .iterator()
          .asScala
      }
      .map { p =>
        new File(p.toString) {
          override def toPath = p
        }
      }
      .toList
  }
}
