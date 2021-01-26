package tests

import lib.CoreTestCase
import lib.Platform
import lib.ui.AuthorizationPageObject
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.MyListsPageObjectFactory
import lib.ui.factories.NavigationUIFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Test

class MyListsTests : CoreTestCase()
{
    @Test
    fun testSaveTwoArticlesAndDeleteOneArticle() {

        val request = "Java"
        val articleTitle1 = "JavaScript"
        val articleTitle2 = "Java (programming language)"
        val nameFolder = "Learning programming"
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val articlePageObject = ArticlePageObjectFactory.get(driver)

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle1)
        if(Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameFolder)
        } else {
            articlePageObject.addArticleToMySaved()
        }
        articlePageObject.closeArticle()

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle2)
        articlePageObject.addArticleToMyList(nameFolder)
        articlePageObject.closeArticle()

        val navigationUI = NavigationUIFactory.get(driver)
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObjectFactory.get(driver)
        myListsPageObject.openFolderByName(nameFolder)
        myListsPageObject.swipeByArticleToDelete(articleTitle1)
        myListsPageObject.openArticle(articleTitle2)
        val openedArticleTitle  = articlePageObject.getArticleTitle()

        assertEquals("Article title have been changed after open", articleTitle2, openedArticleTitle)
    }

    @Test
    fun testSaveFirstArticleToMyList()
    {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val nameFolder = "Learning programming"
        val login= "dementyK"
        val password = "dementy19"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()
        println(articleTitle)

        if(Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameFolder)
        } else {
            articlePageObject.addArticleToMySaved()
        }

        if(Platform.getInstance().isMW()) {
            val auth = AuthorizationPageObject(driver)
            auth.clickButton()
            auth.enterLoginData(login, password)
            auth.submitForm()

            articlePageObject.waitForTitleElement()
            assertEquals("We are not on the same page after login", articleTitle, articlePageObject.getArticleTitle())
            articlePageObject.addArticleToMySaved()
        }
        articlePageObject.closeArticle()

        val navigationUI = NavigationUIFactory.get(driver)
        navigationUI.openNavigation()
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObjectFactory.get(driver)

        if(Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameFolder)
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle.toString())
    }
}