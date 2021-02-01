package lib.ui

import io.qameta.allure.Step
import lib.Platform
import org.openqa.selenium.remote.RemoteWebDriver

abstract class MyListsPageObject(driver: RemoteWebDriver?) : MainPageObject(driver) {
    protected companion object {
        var FOLDER_BY_NAME_TPL = ""
        var ARTICLE_BY_TITLE_TPL = ""
        var REMOVE_FROM_SAVED_TPL = ""
    }

    /*TEMPLATES METHODS*/
    private fun getFolderXpathByName(nameFolder: String): String {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameFolder)
    }

    private fun getSavedArticleXpathByTitle(articleTitle: String): String {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle)
    }

    private fun getRemoveButtonByTitle(articleTitle: String): String {
        return REMOVE_FROM_SAVED_TPL.replace("{TITLE}", articleTitle)
    }
    /*TEMPLATES METHODS*/

    @Step("Открытие папки '{nameFolder}' с сохраненными статьями")
    fun openFolderByName(nameFolder: String) {
        val folderNameXpath = getFolderXpathByName(nameFolder)
        this.waitForElementAndClick(folderNameXpath, "Cannot find folder by name $nameFolder", 5)
    }

    @Step("Свайп для удаления сохраненной статьи с заголовком '{(articleTitle}'")
    fun swipeByArticleToDelete(articleTitle: String) {
        this.waitForArticleToAppearByTitle(articleTitle)
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeElementToLeft(articleTitleXpath, "Cannot find saved article $articleTitle")
        } else {
            Thread.sleep(2000)
            val removeLocator = getRemoveButtonByTitle(articleTitle)
            this.waitForElementAndClick(removeLocator, "Cannot click button to remove article from saved", 10)
        }
        if(Platform.getInstance().isMW()) {
            this.refresh()
        }
        this.waitForArticleToDisappearByTitle(articleTitle)
    }

    @Step("Ожидание появления заголовка '{articleTitle}' в статье")
    private fun waitForArticleToAppearByTitle(articleTitle: String) {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementPresent(articleTitleXpath, "Cannot find saved article by title $articleTitle", 15)
    }

    @Step("Ожидание исчезновения заголовка '{articleTitle}' в статье")
    private fun waitForArticleToDisappearByTitle(articleTitle: String) {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementNotPresent(articleTitleXpath, "Saved article still present with title $articleTitle", 20)
    }

    @Step("Открытие статьи с заголовком '{articleTitle}'")
    fun openArticle(articleTitle: String) {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementAndClick(articleTitleXpath, "Cannot find saved article '$articleTitle'", 5)
    }
}