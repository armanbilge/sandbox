enablePlugins(ScalaJSPlugin, ScalaJSJUnitPlugin)
// libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.2" % Test

def grouped(tests: Seq[TestDefinition]) =
  Seq(Tests.Group("group", tests, Tests.SubProcess(ForkOptions())))

Test / testGrouping := grouped((Test / definedTests).value)
