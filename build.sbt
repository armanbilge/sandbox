ThisBuild / scalaVersion := "3.4.0-RC1-bin-20230829-510aac8-NIGHTLY"

lazy val annotation = project

lazy val foo = project
  .settings(
    // Add annotation as compile-time-only dependency, non-transitive
    Compile / compile := (Compile / compile).dependsOn(annotation / Compile / compile).value,
    Compile / dependencyClasspath += (annotation / Compile / classDirectory).value
  )

lazy val bar = project.dependsOn(foo)
