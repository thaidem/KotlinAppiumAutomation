package tests

import lib.CoreTestCase
import lib.ui.ArticlePageObject
import lib.ui.MyListsPageObject
import lib.ui.NavigationUI
import lib.ui.SearchPageObject
import org.junit.Test

class MyListsTests : CoreTestCase()
{
    @Test
    fun testSaveTwoArticlesAndDeleteOneArticle() {

        val request = "Java"
        val articleTitle1 = "JavaScript"
        val articleTitle2 = "Java (programming language)"
        val nameFolder = "Learning programming"
        val searchPageObject = SearchPageObject(driver)
        val articlePageObject = ArticlePageObject(driver)

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle1)
        articlePageObject.addArticleToMyList(nameFolder)
        articlePageObject.closeArticle()

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle2)
        articlePageObject.addArticleToMyList(nameFolder)
        articlePageObject.closeArticle()

        val navigationUI = NavigationUI(driver)
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObject(driver)
        myListsPageObject.openFolderByName(nameFolder)
        myListsPageObject.swipeByArticleToDelete(articleTitle1)
        myListsPageObject.openArticle(articleTitle2)
        val openedArticleTitle  = articlePageObject.getArticleTitle()

        assertEquals("Article title have been changed after open", articleTitle2, openedArticleTitle)
    }

    @Test
    fun testSaveFirstArticleToMyList()
    {
        val searchPageObject = SearchPageObject(driver)
        val nameFolder = "Learning programming"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language")

        val articlePageObject = ArticlePageObject(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()
        articlePageObject.addArticleToMyList(nameFolder)
        articlePageObject.closeArticle()

        val navigationUI = NavigationUI(driver)
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObject(driver)
        myListsPageObject.openFolderByName(nameFolder)
        myListsPageObject.swipeByArticleToDelete(articleTitle.toString())
    }
}