package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.ArticlePageObject.ArticleLocators.*
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ArticlePageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
//    companion object
//    {
//        private val TITLE = "org.wikipedia:id/view_page_title_text"
//        private val FOOTER_ELEMENT = "//*[contains(@text, 'View page in browser')]"
//        private val OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']"
//        private val OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']"
//        private val ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button"
//        private val MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input"
//        private val MY_LIST_OK_BUTTON = "//*[@text='OK']"
//        private val CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']"
//    }

    enum class ArticleLocators(val locator: String)
    {
        TITLE("org.wikipedia:id/view_page_title_text"),
        FOOTER_ELEMENT("//*[contains(@text, 'View page in browser')]"),
        OPTIONS_BUTTON("//android.widget.ImageView[@content-desc='More options']"),
        OPTIONS_ADD_TO_MY_LIST_BUTTON("//*[@text='Add to reading list']"),
        ADD_TO_MY_LIST_OVERLAY("org.wikipedia:id/onboarding_button"),
        MY_LIST_NAME_INPUT("org.wikipedia:id/text_input"),
        MY_LIST_OK_BUTTON("//*[@text='OK']"),
        CLOSE_ARTICLE_BUTTON("//android.widget.ImageButton[@content-desc='Navigate up']")
    }

    fun waitForTitleElement(): WebElement?
    {
        return this.waitForElementPresent(By.id(TITLE.locator), "Cannot find article title on page!", 15)
    }

    fun getArticleTitle(): String?
    {
        val titleElement = waitForTitleElement()
        return titleElement?.getAttribute("text")
    }

    fun swipeToFooter()
    {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT.locator),"Cannot find the end of the article",10)
    }

    fun addArticleToMyList(nameOfFolder: String)
    {
        this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON.locator),
            "Cannot find button to open article options",
            5
        )

        this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON.locator),
            "Cannot find option to 'Add to reading list'",
            5
        )

        this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY.locator),
            "Cannot find 'Got it' tip overlay",
            5
        )

        this.waitForElementAndClear(
            By.id(MY_LIST_NAME_INPUT.locator),
            "Cannot find input to set name articles folder",
            5
        )

        this.waitForElementAndSendKeys(
            By.id(MY_LIST_NAME_INPUT.locator),
            nameOfFolder,
            "Cannot put text into articles folder input",
            5
        )

        this.waitForElementAndClick(
            By.xpath(MY_LIST_OK_BUTTON.locator),
            "Cannot press button 'OK'",
            5
        )
    }

    fun closeArticle()
    {
        this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON.locator),
            "Cannot close article, cannot find X link",
            5
        )
    }
}