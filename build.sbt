scalaVersion := "3.1.3"
enablePlugins(ScalaNativePlugin)
nativeMode := "release-fast"
nativeLTO := "thin"
libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M5" % Test
