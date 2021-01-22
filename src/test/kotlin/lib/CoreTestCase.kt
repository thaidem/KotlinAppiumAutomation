package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration
import java.util.concurrent.TimeUnit

open class CoreTestCase : TestCase() {
    protected var driver: RemoteWebDriver? = null

    override fun setUp() {
        super.setUp()
        driver = Platform.getInstance().getDriver()

        if (driver is AppiumDriver<*>) {
            (driver as AndroidDriver<*>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
            val width = driver?.manage()?.window()?.size?.getWidth() ?: 0
            val height = driver?.manage()?.window()?.size?.getHeight() ?: 0
            println("width $width, height $height")
            if (width > height) this.rotateScreenPortrait()
        }

        this.openWikiWebPageForMobileWeb()
    }

    override fun tearDown() {
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
        super.tearDown()
    }

    protected fun rotateScreenPortrait() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.PORTRAIT)
        } else {
            println(
                "Method rotateScreenPortrait() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    protected fun rotateScreenLandscape() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.LANDSCAPE)
        } else {
            println(
                "Method rotateScreenLandscape() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    protected fun backgroundApp(seconds: Duration) {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).runAppInBackground(seconds)
        } else {
            println("Method backgroundApp() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }
    }

    private fun openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver?.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS);
            driver?.get("https://en.m.wikipedia.org")
        } else {
            println(
                "Method openWikiWebPageForMobileWeb() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }

    }
}