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
        myListsPageObject.swipeByArticleToDelete(articleTitle)
    }

}