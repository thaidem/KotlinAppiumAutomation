package arc

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class FirstTest  {
      private var driver: AppiumDriver<MobileElement>? = null

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName","Android")
        capabilities.setCapability("deviceName","AndroidTestDevice")
        capabilities.setCapability("platformVersion","8.0")
        capabilities.setCapability("automationName","Appium")
        capabilities.setCapability("appPackage","org.wikipedia")
        capabilities.setCapability("appActivity",".main.MainActivity")
        capabilities.setCapability("app","C:/Develop/KotlinAppiumAutomation/apks/org.wikipedia.apk")

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()?: throw Exception("Driver instance was unable to quit.")
    }

    @Test
    fun firstTest() {
        println("First Test run")
    }
}