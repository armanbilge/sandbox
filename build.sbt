ThisBuild / scalaVersion := "3.1.2-RC1"

lazy val foo = project
  .settings(
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "scala",
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "java",
  )

lazy val bar = project
  .settings(
    scalacOptions ++= List("-release", "8"),
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "scala",
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "java",
    mimaPreviousClassfiles += (foo / projectID).value -> (foo / Compile / classDirectory).value,
    mimaReportBinaryIssues := mimaReportBinaryIssues.dependsOn(foo / Compile / compile).value
  )
