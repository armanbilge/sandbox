ThisBuild / scalaVersion := "2.13.10"
ThisBuild / crossScalaVersions := Seq("2.13.10", "3.2.2")

def mkProject(
    id: String,
    module: String,
    version: String,
    org: String = "com.google.api.grpc"
) =
  Project(id, file(id))
    .enablePlugins(Http4sGrpcPlugin)
    .settings(
      Compile / PB.targets ++= Seq(
        scalapb.gen(grpc = false) -> (Compile / sourceManaged).value / "scalapb"
      ),
      libraryDependencies ++= Seq(
        org % module % version % "protobuf-src" intransitive ()
      )
    )

lazy val java =
  mkProject("java", "protobuf-java", "3.21.7", "com.google.protobuf")
lazy val common =
  mkProject("common", "proto-google-common-protos", "2.9.6").dependsOn(java)

lazy val cloudStorageV2 =
  mkProject("cloud-storage-v2", "proto-google-cloud-storage-v2", "2.9.3-alpha")
    .dependsOn(iamV1)

lazy val iamV1 =
  mkProject("iam-v1", "proto-google-iam-v1", "1.9.3").dependsOn(common)
