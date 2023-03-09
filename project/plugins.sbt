resolvers ++= Resolver.sonatypeOssRepos("snapshots")

addSbtPlugin("org.typelevel" % "sbt-fs2-grpc" % "2.5.11")
addSbtPlugin("io.chrisdavenport" % "sbt-http4s-grpc" % "0.0-23209d1-SNAPSHOT")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.2.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.13.0")
