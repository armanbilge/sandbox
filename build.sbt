import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxOptions, FirefoxProfile}
import org.openqa.selenium.safari.{SafariOptions, SafariDriver}
import org.openqa.selenium.remote.server.{DriverFactory, DriverProvider}

import org.scalajs.jsenv.selenium.SeleniumJSEnv

lazy val a = project.enablePlugins(ScalaJSPlugin)
lazy val b = project.enablePlugins(ScalaJSPlugin)

ThisBuild / Test / jsEnv := {

  val old = (Test / jsEnv).value

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
