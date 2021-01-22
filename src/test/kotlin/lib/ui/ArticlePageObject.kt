package lib.ui

import lib.Platform
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver

abstract class ArticlePageObject(driver: RemoteWebDriver?) : MainPageObject(driver)
{
    protected companion object
    {
        var TITLE = ""
        var FOOTER_ELEMENT = ""
        var OPTIONS_BUTTON = ""
        var OPTIONS_ADD_TO_MY_LIST_BUTTON = ""
        var ADD_TO_MY_LIST_OVERLAY = ""
        var MY_LIST_NAME_INPUT = ""
        var MY_LIST_OK_BUTTON = ""
        var CLOSE_ARTICLE_BUTTON = ""
        var USER_FOLDER_TPL = ""
    }

    /*TEMPLATES METHODS*/
    private fun getUserFolderElement(nameFolder: String): String
    {
        return USER_FOLDER_TPL.replace("{FOLDER}", nameFolder)
    }
    /*TEMPLATES METHODS*/

    fun waitForTitleElement(): WebElement?
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page!", 15)
    }

    fun getArticleTitle(): String?
    {
        val titleElement = waitForTitleElement()
        return when {
            Platform.getInstance().isAndroid() -> titleElement?.getAttribute("text")

            Platform.getInstance().isMW() -> titleElement?.text

            else -> throw Exception("Unknown platform")
        }
    }

    fun swipeToFooter()
    {
        this.swipeUpToFindElement(FOOTER_ELEMENT,"Cannot find the end of the article",10)
    }

    fun addArticleToMyList(nameFolder: String)
    {
        this.waitForElementAndClick(OPTIONS_BUTTON,"Cannot find button to open article options",30)
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to 'Add to reading list'",30)
        if (checkElementsNotPresent(ADD_TO_MY_LIST_OVERLAY))
        {
            val userFolderXpath = getUserFolderElement(nameFolder)
            waitForElementAndClick(userFolderXpath,"Cannot find folder '$nameFolder'",10)

        } else {
            this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,"Cannot find 'Got it' tip overlay",5)
            this.waitForElementAndClear(MY_LIST_NAME_INPUT,"Cannot find input to set name articles folder",5)
            this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT, nameFolder,"Cannot put text into articles folder input",5)
            this.waitForElementAndClick(MY_LIST_OK_BUTTON,"Cannot press button 'OK'",5)
        }
    }

    fun closeArticle()
    {
        this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,"Cannot close article, cannot find X link",5)
    }

    fun assertArticleTitlePresent()
    {
        this.assertElementsPresent(TITLE,"Cannot find article title")
    }
}