import org.scalajs.linker.interface.ModuleSplitStyle

lazy val lower = project.enablePlugins(ScalaJSPlugin)
lazy val upper = project.enablePlugins(ScalaJSPlugin)
lazy val app = project.enablePlugins(ScalaJSPlugin)
  .dependsOn(lower, upper)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule).withModuleSplitStyle(ModuleSplitStyle.SmallestModules)
    }
  )
