ThisBuild / scalaVersion := "3.2.2"

lazy val personOld = project
lazy val personNew = project.settings(
  mimaPreviousClassfiles := Map(
    projectID.value -> (personOld / Compile / classDirectory).value
  ),
  mimaReportBinaryIssues :=
    mimaReportBinaryIssues
      .dependsOn(personOld / Compile / compile)
      .value
)

val appSettings = Seq(
  Compile / unmanagedSourceDirectories += (LocalRootProject / baseDirectory).value / "app" / "src" / "main" / "scala",
  libraryDependencies += "com.github.pureconfig" %% "pureconfig-core" % "0.17.3"
)

val appOld = project.dependsOn(personOld).settings(appSettings)
val appNew = project.dependsOn(personNew).settings(appSettings)
