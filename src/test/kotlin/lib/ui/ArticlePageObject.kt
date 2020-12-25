package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.ArticlePageObject.ArticlePageLocators.*
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ArticlePageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class ArticlePageLocators(val locator: String)
    {
        TITLE("org.wikipedia:id/view_page_title_text"),
        FOOTER_ELEMENT("//*[contains(@text, 'View page in browser')]"),
        OPTIONS_BUTTON("//android.widget.ImageView[@content-desc='More options']"),
        OPTIONS_ADD_TO_MY_LIST_BUTTON("//*[@text='Add to reading list']"),
        ADD_TO_MY_LIST_OVERLAY("org.wikipedia:id/onboarding_button"),
        MY_LIST_NAME_INPUT("org.wikipedia:id/text_input"),
        MY_LIST_OK_BUTTON("//*[@text='OK']"),
        CLOSE_ARTICLE_BUTTON("//android.widget.ImageButton[@content-desc='Navigate up']"),
        USER_FOLDER_TPL("//android.widget.TextView[@text='{FOLDER}']")
    }

    /*TEMPLATES METHODS*/
    private fun getUserFolderElement(nameFolder: String): String
    {
        return USER_FOLDER_TPL.locator.replace("{FOLDER}", nameFolder)
    }
    /*TEMPLATES METHODS*/

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

    fun addArticleToMyList(nameFolder: String)
    {
        this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON.locator),
            "Cannot find button to open article options",
            10
        )

        this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON.locator),
            "Cannot find option to 'Add to reading list'",
            20
        )

        if (checkElementsNotPresent(By.id(ADD_TO_MY_LIST_OVERLAY.locator)))
        {
            val userFolderXpath = getUserFolderElement(nameFolder)
            waitForElementAndClick(By.xpath(userFolderXpath),"Cannot find folder '$nameFolder'",10)

        } else {

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
                nameFolder,
                "Cannot put text into articles folder input",
                5
            )

            this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON.locator),
                "Cannot press button 'OK'",
                5
            )
        }
    }

    fun closeArticle()
    {
        this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_BUTTON.locator),
            "Cannot close article, cannot find X link",
            5
        )
    }

    fun assertArticleTitlePresent()
    {
        this.assertElementsPresent(By.id(TITLE.locator),"Cannot find article title")
    }
}