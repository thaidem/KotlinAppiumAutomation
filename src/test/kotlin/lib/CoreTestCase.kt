package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.util.concurrent.TimeUnit

open class CoreTestCase : TestCase() {

    protected var driver: AppiumDriver<MobileElement>? = null
    private var appiumUrl = "http://127.0.0.1:4723/wd/hub"

    override fun setUp()
    {
        super.setUp()
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/org.wikipedia.apk")

        driver = AndroidDriver(URL(appiumUrl), capabilities)
        (driver as AndroidDriver<MobileElement>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
        val width = driver?.manage()?.window()?.size?.getWidth()?: 0
        val height = driver?.manage()?.window()?.size?.getHeight()?: 0
        println("width $width, height $height")
        if ( width > height) this.rotateScreenPortrait()

//        WebDriverRunner.setWebDriver(driver)
    }

    override fun tearDown()
    {
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
        super.tearDown()
    }

    protected fun rotateScreenPortrait()
    {
        driver?.rotate(ScreenOrientation.PORTRAIT)
    }

    protected fun rotateScreenLandscape()
    {
        driver?.rotate(ScreenOrientation.LANDSCAPE)
    }

    protected fun backgroundApp(seconds: Int) {
        driver?.runAppInBackground(seconds)
    }
}