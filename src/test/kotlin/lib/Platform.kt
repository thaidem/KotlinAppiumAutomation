package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class Platform
{
    companion object {
        private const val PLATFORM_ANDROID = "android"
        private const val PLATFORM_IOS = "ios"
        private const val APPIUM_URL = "http://127.0.0.1:4723/wd/hub"

        private var instance: Platform? = null
        fun getInstance(): Platform
        {
            if (instance == null) {
                instance = Platform()
            }
            return instance as Platform
        }
    }

    fun getDriver(): AppiumDriver<MobileElement>
    {
        val url = URL(APPIUM_URL)
        return when
        {
            this.isAndroid() -> AndroidDriver(url, this.getAndroidDesiredCapabilities())

            this.isIOS() -> IOSDriver(url, this.getIOSDesiredCapabilities())

            else -> throw Exception("Cannot detect type of the driver. Platform value: ${this.getPlatformName()}")
        }
    }

    fun isAndroid() = isPlatform(PLATFORM_ANDROID)

    fun isIOS() = isPlatform(PLATFORM_IOS)

    private fun getAndroidDesiredCapabilities(): DesiredCapabilities
    {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/org.wikipedia.apk")
        return capabilities
    }

    private fun getIOSDesiredCapabilities(): DesiredCapabilities
    {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "IOS")
        capabilities.setCapability("deviceName", "iPhone SE")
        capabilities.setCapability("platformVersion", "11.3")
        capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/wikipedia.app")
        return capabilities
    }

    private fun getPlatformName() = System.getenv("PLATFORM")

    private fun isPlatform(myPlatform: String): Boolean = myPlatform == this.getPlatformName()
}