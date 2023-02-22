ThisBuild / scalaVersion := "2.13.10"
lazy val foo = project
lazy val bar = project.dependsOn(foo)
