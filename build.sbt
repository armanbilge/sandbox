val Scala213 = "2.13.10"
val Scala3 = "3.2.2"

ThisBuild / scalaVersion := Scala3

lazy val root = project
  .in(file("."))
  .aggregate(
    personOld,
    personNew,
    appOld,
    appNew
  )

val crossSettings = List(
  crossScalaVersions := List(scalaVersion.value, Scala213)
)

lazy val personOld = project
  .settings(
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "3.1.0"
  )
  .settings(crossSettings)
lazy val personNew = project
  .settings(crossSettings)
  .settings(
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "3.1.0",
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
  Compile / unmanagedSourceDirectories += {
    if (scalaVersion.value == Scala3)
      (LocalRootProject / baseDirectory).value / "app" / "src" / "main" / "scala-3"
    else
      (LocalRootProject / baseDirectory).value / "app" / "src" / "main" / "scala-2"
  },
  libraryDependencies += {
    if (scalaVersion.value == Scala3)
      "com.github.pureconfig" %% "pureconfig-core" % "0.17.3"
    else
      "com.github.pureconfig" %% "pureconfig" % "0.17.3"
  }
)

val appOld = project
  .dependsOn(personOld)
  .settings(crossSettings)
  .settings(appSettings)
val appNew = project
  .dependsOn(personNew)
  .settings(crossSettings)
  .settings(appSettings)
