package tests

import lib.CoreTestCase
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Test

class ArticleTests : CoreTestCase()
{
    @Test
    fun testCompareArticleTitle()
    {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
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
        val searchPageObject = SearchPageObjectFactory.get(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.waitForTitleElement()
        articlePageObject.swipeToFooter()
    }

    @Test
    fun testCheckTitlePresent()
    {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val request = "Java"
        val articleTitle = "Java (programming language)"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(request)
        searchPageObject.clickByArticleWithTitle(articleTitle)

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.assertArticleTitlePresent()
    }
}