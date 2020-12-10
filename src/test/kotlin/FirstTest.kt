import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.util.concurrent.TimeUnit

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
        (driver as AndroidDriver<MobileElement>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
//        WebDriverRunner.setWebDriver(driver)
    }

    @After
    fun tearDown() {
        driver?.quit()?: throw Exception("Driver instance was unable to quit.")
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

        waitForElementAndClear(By.id(
            "org.wikipedia:id/search_src_text"),
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
            articleTitle)
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


    private fun waitForElementPresent(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val wait = WebDriverWait(driver, timeoutInSeconds)
        wait.withMessage("$errorMessage \n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    private fun waitForElementPresent(by: By, errorMessage: String): WebElement? {
        return waitForElementPresent(by, errorMessage, 5)
    }

    private fun waitForElementAndClick(by: By, errorMessage: String, timeoutInSeconds: Long): WebElement? {
        val element = waitForElementPresent(by, errorMessage, timeoutInSeconds)
        element?.click()
        return element
    }

    private fun waitForElementAndSendKeys(by: By, value: String, errorMessage: String, timeoutInSeconds: Long): WebElement? {
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
        val element = waitForElementPresent(by,errorMessage, timeoutInSeconds)
        element?.clear()
        return element
    }

    private fun assertElementHasText(by: By, value: String, errorMessage: String ) {
        val element = waitForElementPresent(by, "Element not find")
        val textElement = element?.getAttribute("text")
        Assert.assertEquals(errorMessage, value, textElement)
    }

}