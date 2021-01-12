package tests

import lib.CoreTestCase
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
        articlePageObject.addArticleToMyList(nameFolder)
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

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()
        articlePageObject.addArticleToMyList(nameFolder)
        articlePageObject.closeArticle()

        val navigationUI = NavigationUIFactory.get(driver)
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObjectFactory.get(driver)
        myListsPageObject.openFolderByName(nameFolder)
        myListsPageObject.swipeByArticleToDelete(articleTitle.toString())
    }
}