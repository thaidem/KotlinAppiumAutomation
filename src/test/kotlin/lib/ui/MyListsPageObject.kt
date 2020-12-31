package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.MyListsPageObject.MyListsLocators.ARTICLE_BY_TITLE_TPL
import lib.ui.MyListsPageObject.MyListsLocators.FOLDER_BY_NAME_TPL

class MyListsPageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class MyListsLocators(val locator: String)
    {
        FOLDER_BY_NAME_TPL("xpath~//*[@text='{FOLDER_NAME}']"),
        ARTICLE_BY_TITLE_TPL("xpath~//*[@text='{TITLE}']")
    }

    /*TEMPLATES METHODS*/
    private fun getFolderXpathByName(nameFolder: String ):String
    {
        return FOLDER_BY_NAME_TPL.locator.replace("{FOLDER_NAME}", nameFolder)
    }
    private fun getSavedArticleXpathByTitle(articleTitle: String): String
    {
        return ARTICLE_BY_TITLE_TPL.locator.replace("{TITLE}", articleTitle)
    }
    /*TEMPLATES METHODS*/

    fun openFolderByName(nameFolder: String)
    {
        val folderNameXpath = getFolderXpathByName(nameFolder)
        this.waitForElementAndClick(folderNameXpath,"Cannot find folder by name $nameFolder",5)
    }

    fun swipeByArticleToDelete(articleTitle: String)
    {
        this.waitForArticleToAppearByTitle(articleTitle)
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.swipeElementToLeft(articleTitleXpath,"Cannot find saved article $articleTitle")
        this.waitForArticleToDisappearByTitle(articleTitle)
    }

    private fun waitForArticleToAppearByTitle(articleTitle: String)
    {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementPresent(articleTitleXpath,"Cannot find saved article by title $articleTitle",15)
    }

    private fun waitForArticleToDisappearByTitle(articleTitle: String)
    {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementNotPresent(articleTitleXpath,"Saved article still present with title $articleTitle",15)
    }

    fun openArticle(articleTitle: String)
    {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementAndClick(articleTitleXpath,"Cannot find saved article '$articleTitle'",5)
    }

}