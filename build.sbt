ThisBuild / scalaVersion := "3.2.2"
ThisBuild / crossScalaVersions := Seq("2.13.10", "3.2.2")

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

lazy val lib = project
  .settings(
    libraryDependencies += "org.typelevel" %% "kittens" % "3.0.0"
  )
  .dependsOn(personOld % Provided)

val app = project.dependsOn(lib, personNew)
