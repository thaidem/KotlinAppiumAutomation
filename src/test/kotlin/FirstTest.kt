import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.android.AndroidDriver
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

open class FirstTest {
    private var driver: AppiumDriver<MobileElement>? = null

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "C:/Develop/KotlinAppiumAutomation/apks/org.wikipedia.apk")

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
        (driver as AndroidDriver<MobileElement>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
//        WebDriverRunner.setWebDriver(driver)
    }

    @After
    fun tearDown() {
        val width = driver?.manage()?.window()?.size?.getWidth()?: 0
        val height = driver?.manage()?.window()?.size?.getHeight()?: 0
        println("Width $width, height $height")
        if ( width > height) {
            driver?.rotate(ScreenOrientation.PORTRAIT)
        }
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }

    @Test
    fun firstTest() {
        waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find search Wikipedia input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5
        )

        waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15
        )
    }

    @Test
    fun testCancelSearch() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5
        )

        waitForElementAndClear(
            By.id(
                "org.wikipedia:id/search_src_text"
            ),
            "Cannot find search field",
            5
        )

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X cancel search",
            5
        )

        waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page ",
            5
        )
    }

    @Test
    fun testCompareArticleTitle() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find article",
            5
        )

        val titleElement = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
        )
        val articleTitle = titleElement?.getAttribute("text")

        Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            articleTitle
        )
    }

    @Test
    fun testAssertElementHasText() {
        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        assertElementHasText(
            By.id("org.wikipedia:id/search_src_text"),
            "Search…",
            "Element does not contain expected text"
        )
    }

    @Test
    fun testCancelSearchAndCleaningResult() {
        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Kotlin",
            "Cannot find search input",
            5
        )

        checkItemsAppeared(
            By.id("org.wikipedia:id/page_list_item_container"), "Articles not visible"
        )

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X cancel search",
            5
        )

        checkItemsDisappeared(
            By.id("org.wikipedia:id/page_list_item_container"), "Articles visible"
        )
    }

    @Test
    fun testCheckWordsInResult() {
        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Kotlin",
            "Cannot find search input",
            5
        )

        checkWordsInResult(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Kotlin",
            "No items with this locator found",
            5
        )
    }

    @Test
    fun testSwipeArticle() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Appium",
            "Cannot find search input",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, 'Appium')]"),
            "Cannot find 'Appium' article in search",
            5
        )

        waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
        )

        swipeUpToFindElement(
            By.xpath("//*[contains(@text, 'View page in browser')]"),
            "Cannot find the end of the article",
            10
        )
    }

    @Test
    fun saveFirstArticleToMyList() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find article",
            5
        )

        waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
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

        waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5
        )

        waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name articles folder",
            5
        )

        val nameFolder = "Learning programming"

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

        waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5
        )

        waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button 'Me List'",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@text='$nameFolder']"),
            "Cannot find created folder",
            5
        )

        swipeElementToLeft(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot find saved article"
        )

        waitForElementNotPresent(
            By.xpath("//*[@text='Java (programming language)']"),
            "Cannot delete saved article",
            5
        )
    }

    @Test
    fun testAmountOfNotSearch() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        val searchLine = "Linkin Park Discography"

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
        )

        val searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']"

        waitForElementPresent(
            By.xpath(searchResultLocator),
            "Cannot find anything by the request $searchLine",
            15
        )

        val amountSearchResult = getAmountOfElements(By.xpath(searchResultLocator))
        println(amountSearchResult)
        assertTrue(
            amountSearchResult ?: 0 > 0,
            "We found too few results!"
        )
    }

    @Test
    fun testAmountOfEmptySearch() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        val searchLine = "asfgdfhgjk43654"

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
        )

        val searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']"

        val emptyResultLabel = "//*[contains(@text, 'No results found')]"

        waitForElementPresent(
            By.xpath(emptyResultLabel),
            "Cannot find empty result label by the request '$searchLine'",
            15
        )

        assertElementNotPresent(
            By.xpath(searchResultLocator),
            "We've found some result by request $searchLine"
        )
    }

    @Test
    fun testChangeScreenOrientationOnSearchResult() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        val searchLine = "Java"

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            searchLine,
            "Cannot find search input",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find 'Object-oriented programming language' topic searching by $searchLine",
            15
        )

        val titleBeforeRotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
        )

        driver?.rotate(ScreenOrientation.LANDSCAPE)

            val titleAfterRotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
        )

        assertEquals(
            "Article title have been changed after screen rotation",
            titleBeforeRotation,
            titleAfterRotation )

//        driver?.rotate(ScreenOrientation.PORTRAIT)
//
//        val titleAfterSecondRotation = waitForElementAndGetAttribute(
//            By.id("org.wikipedia:id/view_page_title_text"),
//            "text",
//            "Cannot find title of article",
//            15
//        )
//
//        assertEquals(
//            "Article title have been changed after second screen rotation",
//            titleBeforeRotation,
//            titleAfterSecondRotation )
    }

    @Test
    fun testCheckArticleInBackground() {

        waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5
        )

        waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find article",
            5
        )

        driver?.runAppInBackground(3)

        waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Object-oriented programming language')]"),
            "Cannot find article after returning from background",
            5
        )
    }

    @Test
    fun saveTwoArticlesAndDeleteOneArticle() {

        val request = "Java"
        val articleTitle1 = "JavaScript"
        val articleTitle2 = "Java (programming language)"
        val nameFolder = "Learning programming"

        inputSearchRequest(request)
        selectAndSaveArticle(articleTitle1, nameFolder)

        inputSearchRequest(request)
        selectAndSaveArticle(articleTitle2, nameFolder)

        waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button 'Me List'",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@text='$nameFolder']"),
            "Cannot find created folder",
            10
        )

        swipeElementToLeft(
            By.xpath("//*[@text='$articleTitle1']"),
            "Cannot find saved article '$articleTitle1'"
        )

        waitForElementNotPresent(
            By.xpath("//*[@text='$articleTitle1']"),
            "Cannot delete saved article '$articleTitle1'",
            5
        )

        waitForElementAndClick(
            By.xpath("//*[@text='$articleTitle2']"),
            "Cannot find saved article '$articleTitle2'",
            5
        )

        val openedArticleTitle  = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
        )

        assertEquals(
            "Article title have been changed after open",
            articleTitle2,
            openedArticleTitle)
    }

    private fun inputSearchRequest(request: String) {

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

    private fun selectAndSaveArticle(articleTitle: String, nameFolder: String) {

        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '$articleTitle')]"),
            "Cannot find article",
            5
        )

        waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
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

        if (checkItemsDisappeared(onBoardingButton)) {
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


    private fun waitForElementAndGetAttribute(
        by: By,attribute: String, errorMessage: String, timeoutInSeconds: Long): String? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        return element?.getAttribute(attribute)
    }


    private fun assertElementNotPresent(by: By, errorMessage: String) {
        val amountOfElement = getAmountOfElements(by)
        if (amountOfElement ?: 0 > 0) {
            val defaultMessage = "An element '$by' supposed to be not present"
            throw AssertionError("$defaultMessage $errorMessage")
        }
    }

    private fun getAmountOfElements(by: By) = driver?.findElements(by)?.size

    protected fun swipeElementToLeft(by: By, errorMessage: String) {
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
            .waitAction(2000)
            .moveTo(leftX, middleY)
            .release()
            .perform()
    }

    protected fun swipeUp(timeOfSwipe: Int) {
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

    protected fun swipeUpQuick() = swipeUp(500)

    protected fun swipeUpToFindElement(by: By, errorMessage: String, maxSwipes: Int) {
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

    private fun waitForElementPresent(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    private fun waitForAllElementsPresent(
        by: By,
        errorMessage: String,
        timeoutInSeconds: Long
    ): MutableList<WebElement>? {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by))
    }

    private fun waitForElementPresent(by: By, errorMessage: String): WebElement? {
        return waitForElementPresent(by, errorMessage, 5)
    }

    private fun waitForElementAndClick(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.click()
        return element
    }

    private fun waitForElementAndSendKeys(
        by: By,
        value: String,
        errorMessage: String,
        timeoutInSeconds: Long
    ): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.sendKeys(value)
        return element
    }

    private fun waitForElementNotPresent(by: By, errorMessage: String, timeoutInSeconds: Long): Boolean {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    private fun waitForElementAndClear(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.clear()
        return element
    }

    private fun assertElementHasText(by: By, value: String, errorMessage: String) {
        val element = waitForElementPresent(by, "Element not find")
        val textElement = element?.getAttribute("text")
        assertEquals(errorMessage, value, textElement)
    }

    private fun checkItemsAppeared(by: By, errorMessage: String) {
        val flag = driver?.findElements(by)?.isNotEmpty()
        assertEquals(errorMessage, flag, true)

    }

    private fun checkItemsDisappeared(by: By, errorMessage: String) {
        val flag = driver?.findElements(by)?.isEmpty()
        assertEquals(errorMessage, flag, true)
    }

    private fun checkItemsDisappeared(by: By): Boolean {
        return driver?.findElements(by)?.isEmpty()!!
    }

    private fun checkWordsInResult(by: By, value: String, errorMessage: String, timeoutInSeconds: Long) {
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