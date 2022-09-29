lazy val before = project
  .settings(
    scalaVersion := "3.1.3",
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "scala",
  )

lazy val after = project
  .settings(
    scalaVersion := "3.2.0-RC1-bin-20220307-6dc591a-NIGHTLY",
    Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "src" / "main" / "scala",
    mimaPreviousClassfiles += (before / projectID).value -> (before / Compile / classDirectory).value,
    mimaReportBinaryIssues := mimaReportBinaryIssues.dependsOn(before / Compile / compile).value
  )