ThisBuild / scalaVersion := "2.13.10"

lazy val fooOld = project
lazy val fooNew = project
lazy val bar = project.dependsOn(fooOld % Provided)
lazy val app = project.dependsOn(bar, fooNew)
