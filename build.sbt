import java.util.zip.ZipFile
import scala.collection.JavaConverters._

scalaVersion := "2.13.8"
libraryDependencies += "org.typelevel" %% "cats-kernel" % "2.8.0" classifier "sources"

Compile / managedSources ++= (Compile / dependencyClasspathAsJars).value
  .flatMap { attr =>
    if (attr.data.toString().endsWith("-sources.jar"))
      new ZipFile(attr.data).entries().asScala.flatMap { entry =>
        if (entry.getName.endsWith(".scala"))
          List(
            file(url(s"jar:file://${attr.data.getAbsolutePath()}!/${entry.getName}").getFile())
          )
        else
          Nil
      }
    else Nil
  }.take(1)

// Compile / doc := {
//   (Compile / dependencyClasspathAsJars).value.foreach { attr =>
//     if (attr.data.toString().endsWith("-sources.jar"))
//       IO.unzip(attr.data, (Compile / sourceManaged).value)
//   }
//   (Compile / doc).value
// }

// // Compile / sourceManaged ++= (Compile / dependencyClasspathAsJars).value.flatMap[java.io.File, Seq[java.io.File]] { attr =>
// //   ???
// // }
