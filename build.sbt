scalaVersion := "2.13.8"
libraryDependencies += "org.typelevel" %% "cats-kernel" % "2.8.0" classifier "sources"

Compile / doc / apiMappings ++= {
  (Compile / dependencyClasspathAsJars).value.foreach { attr =>
    if (attr.data.toString().endsWith("-sources.jar"))
      IO.unzip(attr.data, (Compile / sourceManaged).value)
  }
  val base = (Compile / sourceManaged).value
  (Compile / managedSources).value.map(file => file -> url(file.relativeTo(base).get.getPath)).toMap
}
