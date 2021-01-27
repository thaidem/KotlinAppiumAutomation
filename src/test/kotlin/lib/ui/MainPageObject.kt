package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import junit.framework.TestCase
import lib.Platform
import lib.PlatformTouchAction
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.test.assertTrue

open class MainPageObject(private val driver: RemoteWebDriver?) {

    fun waitForElementAndGetAttribute(
        locator: String,
        attribute: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): String? {
        val element = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        return element?.getAttribute(attribute)
    }

    fun assertElementNotPresent(locator: String, errorMessage: String) {
        val amountOfElement = getAmountOfElements(locator)
        if (amountOfElement ?: 0 > 0) {
            val defaultMessage = "An element '$locator' supposed to be not present"
            throw AssertionError("$defaultMessage $errorMessage")
        }
    }

    fun getAmountOfElements(locator: String): Int? {
        val by = this.getLocatorByString(locator)
        return driver?.findElements(by)?.size
    }

    fun isElementPresent(locator: String) = this.getAmountOfElements(locator)?:0 > 0

    fun tryClickElementWithFewAttempts(locator: String, errorMessage: String, amountOfAttempts: Int) {
        var currentAttempts = 0
        var needMoreAttempts = true

        while(needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 2)
                needMoreAttempts = false
            } catch (e: Exception) {
                if(currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 2)
                }
            }
            ++currentAttempts
        }
    }

    fun swipeElementToLeft(locator: String, errorMessage: String) {
        if (driver is AppiumDriver<*>) {
            val element = waitForElementPresent(locator, errorMessage, 10)
            val leftX = element?.location?.getX()
            println(leftX)
            val rightX = leftX!! + element.size.getWidth()
            println(rightX)
            val upperY = element.location.getY()
            println(upperY)
            val lowerY = upperY + element.size.getHeight()
            println(lowerY)
            val middleY = (upperY + lowerY) / 2
            println(middleY)
            val action = PlatformTouchAction(driver)
            action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform()
        } else {
            println("Method swipeElementToLeft() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }

    }

    private fun swipeUp(timeOfSwipe: Long) {
        if (driver is AppiumDriver<*>) {
            val action = PlatformTouchAction(driver)
            val size = driver.manage()?.window()?.size
            val x: Int? = size?.width?.div(2)
            val startY = size?.height?.times(0.8)?.toInt()
            val endY = size?.height?.times(0.2)?.toInt()

            action
                .press(PointOption.point(x!!, startY!!))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY!!)).release()
                .perform()
        } else {
            println("Method swipeUp(() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }

    }

    private fun swipeUpQuick() = swipeUp(300)

    fun scrollWebPageUp() {
        if(Platform.getInstance().isMW()) {
            val jSExecutor: JavascriptExecutor = driver as JavascriptExecutor
            jSExecutor.executeScript("window.scrollBy(0,250)")
        } else {
            println(
                "Method scrollWebPageUp() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }
    }

    fun scrollWebPageTillElementNotVisible(locator: String, errorMessage: String, maxSwipes: Int) {
        var alreadySwipe = 0
        val element = this.waitForElementPresent(locator, errorMessage)

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp()
            alreadySwipe++

            if(alreadySwipe > maxSwipes) {
                Assert.assertTrue(errorMessage, element!!.isDisplayed)
            }
        }
    }

    private fun isElementLocatedOnTheScreen(locator: String): Boolean {
        var elementLocationByY = this.waitForElementPresent(locator, "Cannot find element", 1)?.location?.getY()
        if (Platform.getInstance().isMW()) {
            val jSExecutor: JavascriptExecutor = driver as JavascriptExecutor
            val jsResult = jSExecutor.executeScript("return window.pageYOffset")
            elementLocationByY = elementLocationByY?.minus(jsResult.toString().toInt())
        }
        val screenSizeByY = driver?.manage()?.window()?.size?.height
        return elementLocationByY ?: 0 < screenSizeByY ?: 0
    }

    fun swipeUpToFindElement(locator: String, errorMessage: String, maxSwipes: Int) {
        var alreadySwiped = 0

        while (getAmountOfElements(locator) == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up.\n $errorMessage", 0)
                return
            }
            swipeUpQuick()
            ++alreadySwiped
        }
    }

    fun waitForElementPresent(locator: String, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    private fun waitForAllElementsPresent(
        locator: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): MutableList<WebElement>? {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))
    }

    fun waitForElementPresent(locator: String, errorMessage: String): WebElement? {
        return waitForElementPresent(locator, errorMessage, 5)
    }

    fun waitForElementAndClick(locator: String, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        element?.click()
        return element
    }

    fun waitForElementAndSendKeys(
        locator: String,
        value: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): WebElement? {
        val element = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        Actions(driver).moveToElement(element).sendKeys(value).perform()
//        element?.sendKeys(value)
        return element
    }

    fun waitForElementAndTAB(
        locator: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): WebElement? {
        val element = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        element?.sendKeys(Keys.TAB)
        return element
    }

    fun waitForElementNotPresent(locator: String, errorMessage: String, timeoutInSeconds: Long): Boolean {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    fun waitForElementAndClear(locator: String, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(locator, errorMessage, timeoutInSeconds)
        element?.clear()
        return element
    }

    fun assertElementHasText(locator: String, value: String, errorMessage: String) {
        val element = waitForElementPresent(locator, "Element not find")
        val attr = if (driver is AppiumDriver<*>) {
            "text"
        } else {
            "placeholder"
        }
        val textElement = element?.getAttribute(attr)
        TestCase.assertEquals(errorMessage, value, textElement)
    }

    fun assertElementsPresent(locator: String, errorMessage: String) {
        val by = this.getLocatorByString(locator)
        val flag = driver?.findElements(by)?.isNotEmpty()
        TestCase.assertEquals(errorMessage, flag, true)
    }

    fun checkElementsPresent(locator: String): Boolean {
        val by = this.getLocatorByString(locator)
        return driver?.findElements(by)?.isNotEmpty()!!
    }

    fun assertElementsNotPresent(locator: String, errorMessage: String) {
        val by = this.getLocatorByString(locator)
        val flag = driver?.findElements(by)?.isEmpty()
        TestCase.assertEquals(errorMessage, flag, true)
    }

    fun checkElementsNotPresent(locator: String): Boolean {
        val by = this.getLocatorByString(locator)
        return driver?.findElements(by)?.isEmpty()!!
    }

    fun checkWordsInResult(locator: String, value: String, errorMessage: String, timeoutInSeconds: Long) {
        val elements = waitForAllElementsPresent(locator, errorMessage, timeoutInSeconds)
        val attr = if (driver is AppiumDriver<*>) {
            "text"
        } else {
            "title"
        }
        elements?.forEach {
            println(it.getAttribute(attr))
            assertTrue(
                value in it.getAttribute(attr),
                "String '${it.getAttribute(attr)}' does not contain '$value'"
            )
        }
    }

    private fun getLocatorByString(locatorWithType: String): By {
        val explodedLocator = locatorWithType.split("~")
        val byType = explodedLocator[0]
        val locator = explodedLocator[1]

        return when (byType) {
            "xpath" -> By.xpath(locator)
            "id" -> By.id(locator)
            "css" -> By.cssSelector(locator)
            else -> throw IllegalArgumentException("Cannot get type of locator. locator '$locator'")
        }
    }

    fun refresh() {
        driver?.navigate()?.refresh()
    }

    fun getURL(): String? = driver?.currentUrl
}