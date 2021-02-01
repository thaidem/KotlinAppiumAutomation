package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.junit.After
import org.junit.Before
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.FileOutputStream
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit.SECONDS

open class CoreTestCase {
    protected var driver: RemoteWebDriver? = null

    @Before
    @Step("Run driver and session")
    fun setUp() {
        driver = Platform.getInstance().getDriver()
        this.createAllurePropertyFile()

        if (driver is AppiumDriver<*>) {
            (driver as AndroidDriver<*>).manage().timeouts().implicitlyWait(15, SECONDS)
            val width = driver?.manage()?.window()?.size?.getWidth() ?: 0
            val height = driver?.manage()?.window()?.size?.getHeight() ?: 0
            println("width $width, height $height")
            if (width > height) this.rotateScreenPortrait()
        }

        this.openWikiWebPageForMobileWeb()
    }

    @After
    @Step("Remove driver and session")
    fun tearDown() {
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }

    @Step("Rotate screen to portrait mode")
    protected fun rotateScreenPortrait() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.PORTRAIT)
        } else {
            println(
                "Method rotateScreenPortrait() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    @Step("Rotate screen to landscape mode")
    protected fun rotateScreenLandscape() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.LANDSCAPE)
        } else {
            println(
                "Method rotateScreenLandscape() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    @Step("Перевести приложение в фоновый режим (не работает в режиме Mobile Web)")
    protected fun backgroundApp(seconds: Duration) {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).runAppInBackground(seconds)
        } else {
            println("Method backgroundApp() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }
    }

    @Step("Открыть главную страницу сайта Википедия (не работает в режиме Android и IOS)")
    private fun openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver?.manage()?.timeouts()?.implicitlyWait(10, SECONDS)
            driver?.get("https://en.m.wikipedia.org")
        } else {
            println(
                "Method openWikiWebPageForMobileWeb() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    private fun createAllurePropertyFile() {
        val path = System.getProperty("allure.results.directory")
        try {
            val props = Properties()
            val fos = FileOutputStream("$path/environment.properties")
            println(Platform.getInstance().getPlatformName())
            props.setProperty("Environment", Platform.getInstance().getPlatformName())
            props.store(fos, "See Wiki Allure")
            println(props.toString())
            fos.close()
        } catch (e: Exception) {System.err.println("IO problem when writing allure properties file")
            e.printStackTrace()

        }

    }
}