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

open class CoreTestCase : TestCase()
{
    companion object {
        private const val PLATFORM_ANDROID = "android"
        private const val PLATFORM_IOS = "ios"
    }

    protected var driver: AppiumDriver<MobileElement>? = null
    private val appiumUrl = "http://127.0.0.1:4723/wd/hub"

    override fun setUp()
    {
        super.setUp()
        val capabilities = this.getCapabilitiesByPlatformEnv()
        driver = this.getDriverByPlatformEnv(capabilities)
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

    private fun getCapabilitiesByPlatformEnv() : DesiredCapabilities
    {
        val capabilities = DesiredCapabilities()
        when (val platform = System.getenv("PLATFORM")) {
            PLATFORM_ANDROID -> {
                capabilities.setCapability("platformName", "Android")
                capabilities.setCapability("deviceName", "AndroidTestDevice")
                capabilities.setCapability("platformVersion", "8.0")
                capabilities.setCapability("automationName", "Appium")
                capabilities.setCapability("appPackage", "org.wikipedia")
                capabilities.setCapability("appActivity", ".main.MainActivity")
                capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/org.wikipedia.apk")
            }
            PLATFORM_IOS -> {
                capabilities.setCapability("platformName", "IOS")
                capabilities.setCapability("deviceName", "iPhone SE")
                capabilities.setCapability("platformVersion", "11.3")
                capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/wikipedia.app")
            }
            else -> {
                throw Exception("Cannot get run platform from env variable. Platform value '$platform'")
            }
        }
        return capabilities
    }

    private fun getDriverByPlatformEnv(capabilities: DesiredCapabilities): AppiumDriver<MobileElement> {
        return when (val platform = System.getenv("PLATFORM")) {
            PLATFORM_ANDROID -> {
                AndroidDriver(URL(appiumUrl), capabilities)
            }
            PLATFORM_IOS -> {
                IOSDriver(URL(appiumUrl), capabilities)
            }
            else -> {
                throw Exception("Cannot get run platform from env variable. Platform value '$platform'")
            }
        }
    }
}