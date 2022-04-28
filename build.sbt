import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.safari.{SafariOptions, SafariDriver}
import org.scalajs.jsenv.selenium.SeleniumJSEnv

ThisBuild / parallelExecution := false

lazy val a = project.enablePlugins(ScalaJSPlugin).settings(commonSettings)
lazy val b = project.enablePlugins(ScalaJSPlugin).settings(commonSettings)

lazy val commonSettings = Seq(
  Test / jsEnv := {
    sys.env("BROWSER") match {
      case "chrome" =>
        val options = new ChromeOptions()
        options.setHeadless(true)
        new SeleniumJSEnv(options, SeleniumJSEnv.Config())
      case "safari" => 
        val options = new SafariOptions()
        new SeleniumJSEnv(options, SeleniumJSEnv.Config())
    }
  }
)
