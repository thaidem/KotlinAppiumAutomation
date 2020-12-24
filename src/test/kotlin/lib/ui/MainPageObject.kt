package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import junit.framework.TestCase
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import kotlin.test.assertTrue

open class MainPageObject(private val driver: AppiumDriver<MobileElement>?) {

    fun inputSearchRequest(request: String) {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            request,
            "Cannot find search input",
            5
        )
    }

    fun selectAndSaveArticle(articleTitle: String, nameFolder: String) {

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '$articleTitle')]"),
            "Cannot find article",
            5
        )

        waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            20
        )

        waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to 'Add to reading list'",
            5
        )

        val onBoardingButton  = By.id("org.wikipedia:id/onboarding_button")

        if (checkElementsNotPresent(onBoardingButton)) {
            waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='$nameFolder']"),
                "Cannot find '$nameFolder' folder",
                5
            )
        } else {
            waitForElementAndClick(
                onBoardingButton,
                "Cannot find 'Got it' tip overlay",
                5
            )

            waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name articles folder",
                5
            )

            waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameFolder,
                "Cannot put text into articles folder input",
                5
            )

            waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press button 'OK'",
                5
            )
        }

        waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            10
        )
    }

    fun waitForElementAndGetAttribute(
        by: By, attribute: String, errorMessage: String, timeoutInSeconds: Long): String? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        return element?.getAttribute(attribute)
    }

    fun assertElementNotPresent(by: By, errorMessage: String) {
        val amountOfElement = getAmountOfElements(by)
        if (amountOfElement ?: 0 > 0) {
            val defaultMessage = "An element '$by' supposed to be not present"
            throw AssertionError("$defaultMessage $errorMessage")
        }
    }

    fun getAmountOfElements(by: By) = driver?.findElements(by)?.size

    fun swipeElementToLeft(by: By, errorMessage: String) {
        val element = waitForElementPresent(by, errorMessage, 10)
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
        val action = TouchAction(driver)
        action
            .press(rightX, middleY)
            .waitAction(150)
            .moveTo(leftX, middleY)
            .release()
            .perform()
    }

    fun swipeUp(timeOfSwipe: Int) {
        val action = TouchAction(driver)
        val size = driver?.manage()?.window()?.size
        val x: Int? = size?.width?.div(2)
        val startY = size?.height?.times(0.8)?.toInt()
        val endY = size?.height?.times(0.2)?.toInt()

        action
            .press(x!!, startY!!)
            .waitAction(timeOfSwipe)
            .moveTo(x, endY!!).release()
            .perform()
    }

    fun swipeUpQuick() = swipeUp(500)

    fun swipeUpToFindElement(by: By, errorMessage: String, maxSwipes: Int) {
        var alreadySwiped = 0

        while (driver?.findElements(by)?.size == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up.\n $errorMessage", 0)
                return
            }

            swipeUpQuick()
            ++alreadySwiped
        }
    }

    fun waitForElementPresent(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForAllElementsPresent(
        by: By,
        errorMessage: String,
        timeoutInSeconds: Long
    ): MutableList<WebElement>? {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))
    }

    fun waitForElementPresent(by: By, errorMessage: String): WebElement? {
        return waitForElementPresent(by, errorMessage, 5)
    }

    fun waitForElementAndClick(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.click()
        return element
    }

    fun waitForElementAndSendKeys(
        by: By,
        value: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.sendKeys(value)
        return element
    }

    fun waitForElementNotPresent(by: By, errorMessage: String, timeoutInSeconds: Long): Boolean {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    fun waitForElementAndClear(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.clear()
        return element
    }

    fun assertElementHasText(by: By, value: String, errorMessage: String) {
        val element = waitForElementPresent(by, "Element not find")
        val textElement = element?.getAttribute("text")
        TestCase.assertEquals(errorMessage, value, textElement)
    }

    fun assertElementsPresent(by: By, errorMessage: String) {
        val flag = driver?.findElements(by)?.isNotEmpty()
        println( driver?.findElements(by)?.toList())
        TestCase.assertEquals(errorMessage, flag, true)
    }

    fun checkElementsPresent(by: By): Boolean {
        return driver?.findElements(by)?.isNotEmpty()!!
    }

    fun assertElementsNotPresent(by: By, errorMessage: String) {
        val flag = driver?.findElements(by)?.isEmpty()
        TestCase.assertEquals(errorMessage, flag, true)
    }

    fun checkElementsNotPresent(by: By): Boolean {
        return driver?.findElements(by)?.isEmpty()!!
    }

    fun checkWordsInResult(by: By, value: String, errorMessage: String, timeoutInSeconds: Long) {
        val elements = waitForAllElementsPresent(by, errorMessage, timeoutInSeconds)
        elements?.forEach {
            println(it.getAttribute("text"))
            assertTrue(
                value in it.getAttribute("text"),
                "String '${it.getAttribute("text")}' does not contain '$value'"
            )
        }
    }
}