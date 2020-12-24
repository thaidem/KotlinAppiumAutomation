package tests

import lib.CoreTestCase
import lib.ui.ArticlePageObject
import lib.ui.SearchPageObject
import org.junit.Test

class ChangeAppConditionTests : CoreTestCase()
{
    @Test
    fun testChangeScreenOrientationOnSearchResult()
    {
        val searchPageObject = SearchPageObject(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language")

        val articlePageObject = ArticlePageObject(driver)
        val titleBeforeRotation = articlePageObject.getArticleTitle()
        this.rotateScreenLandscape()
        val titleAfterRotation = articlePageObject.getArticleTitle()
        assertEquals("Article title have been changed after screen rotation", titleBeforeRotation, titleAfterRotation )

        this.rotateScreenPortrait()
        val titleAfterSecondRotation = articlePageObject.getArticleTitle()
        assertEquals("Article title have been changed after second screen rotation", titleBeforeRotation, titleAfterSecondRotation )
    }

    @Test
    fun testCheckArticleInBackground()
    {
        val searchPageObject = SearchPageObject(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.waitForSearchResult("Object-oriented programming language")
        this.backgroundApp(3)
        searchPageObject.waitForSearchResult("Object-oriented programming language")
    }
}