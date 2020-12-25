package tests

import lib.CoreTestCase
import lib.ui.ArticlePageObject
import lib.ui.SearchPageObject
import org.junit.Test

class ArticleTests : CoreTestCase()
{
    @Test
    fun testCompareArticleTitle()
    {
        val searchPageObject = SearchPageObject(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language")

        val articlePageObject = ArticlePageObject(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()

        assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            articleTitle
        )
    }

    @Test
    fun testSwipeArticle()
    {
        val searchPageObject = SearchPageObject(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Appium")
        searchPageObject.clickByArticleWithSubstring("Appium")

        val articlePageObject = ArticlePageObject(driver)
        articlePageObject.waitForTitleElement()
        articlePageObject.swipeToFooter()
    }

    @Test
    fun testCheckTitlePresent()
    {
        val searchPageObject = SearchPageObject(driver)
        val request = "Java"
        val articleTitle = "Java (programming language)"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(request)
        searchPageObject.clickByArticleWithTitle(articleTitle)

        val articlePageObject = ArticlePageObject(driver)
        articlePageObject.assertArticleTitlePresent()
    }
}