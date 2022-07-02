import java.util.zip.ZipFile
import scala.collection.JavaConverters._

scalaVersion := "2.13.8"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.8.0" classifier "sources"

Compile / doc := {
  (Compile / dependencyClasspathAsJars).value.foreach { attr =>
    if (attr.data.toString().endsWith("-sources.jar"))
      IO.unzip(attr.data, (Compile / sourceManaged).value)
  }
  (Compile / doc).value
}

// Compile / sourceManaged ++= (Compile / dependencyClasspathAsJars).value.flatMap[java.io.File, Seq[java.io.File]] { attr =>
//   ???
// }
