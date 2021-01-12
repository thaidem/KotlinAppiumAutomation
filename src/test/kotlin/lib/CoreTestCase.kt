package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import java.time.Duration
import java.util.concurrent.TimeUnit

open class CoreTestCase : TestCase()
{
    protected var driver: AppiumDriver<MobileElement>? = null

    override fun setUp()
    {
        super.setUp()
        driver = Platform.getInstance().getDriver()
        (driver as AndroidDriver<MobileElement>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)

        val width = driver?.manage()?.window()?.size?.getWidth()?: 0
        val height = driver?.manage()?.window()?.size?.getHeight()?: 0
        println("width $width, height $height")
        if ( width > height) this.rotateScreenPortrait()
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