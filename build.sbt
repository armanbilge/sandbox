ThisBuild / scalaVersion := "3.2.2"

Global / resolvers ++= Resolver.sonatypeOssRepos("snapshots")

val http4s = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .enablePlugins(Http4sGrpcPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %%% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "org.http4s" %%% "http4s-ember-server" % "0.23.18",
      "org.http4s" %%% "http4s-ember-client" % "0.23.18"
    ),
    Compile / PB.targets += scalapb.gen(grpc = false) -> (Compile / sourceManaged).value / "scalapb",
    Compile / PB.protoSources += (ThisBuild / baseDirectory).value / "protobuf"
  )
  .jvmSettings(
    fork := true
  )
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )

val fs2 = project
  .enablePlugins(Fs2Grpc)
  .settings(
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %%% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "io.grpc" % "grpc-netty-shaded" % scalapb.compiler.Version.grpcJavaVersion
    ),
    Compile / PB.protoSources += (ThisBuild / baseDirectory).value / "protobuf",
    fork := true
  )

