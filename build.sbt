ThisBuild / scalaVersion := "3.3.0-RC1-bin-20221115-e587a81-NIGHTLY"

lazy val fooOld = project
lazy val fooNew = project
lazy val bar = project.dependsOn(fooOld % Provided)
lazy val app = project.dependsOn(bar, fooNew)
