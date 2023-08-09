scalaVersion := "3.3.0"

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

libraryDependencies ++= Seq(
    "com.google.protobuf" % "protobuf-java" % "3.24.0" % "protobuf-src" intransitive ()
)
