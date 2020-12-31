package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL
import java.time.Duration
import java.util.concurrent.TimeUnit

open class IOSTestCase : TestCase() {

    protected var driver: AppiumDriver<MobileElement>? = null
    private var appiumUrl = "http://127.0.0.1:4723/wd/hub"

    override fun setUp()
    {
        super.setUp()
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName", "IOS")
        capabilities.setCapability("deviceName", "iPhone SE")
        capabilities.setCapability("platformVersion", "11.3")
        capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/wikipedia.app")

        driver = IOSDriver(URL(appiumUrl), capabilities)
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

    protected fun backgroundApp(seconds: Duration) {
        driver?.runAppInBackground(seconds)
    }
}