package tests

import lib.CoreTestCase
import lib.Platform
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Assert
import org.junit.Test
import java.time.Duration

class ChangeAppConditionTests : CoreTestCase() {
    @Test
    fun testChangeScreenOrientationOnSearchResult() {
        if(Platform.getInstance().isMW()) {
            return
        }
        val searchPageObject = SearchPageObjectFactory.get(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        val titleBeforeRotation = articlePageObject.getArticleTitle()
        this.rotateScreenLandscape()
        val titleAfterRotation = articlePageObject.getArticleTitle()
        Assert.assertEquals("Article title have been changed after screen rotation", titleBeforeRotation, titleAfterRotation)

        this.rotateScreenPortrait()
        val titleAfterSecondRotation = articlePageObject.getArticleTitle()
        Assert.assertEquals(
            "Article title have been changed after second screen rotation",
            titleBeforeRotation,
            titleAfterSecondRotation
        )
    }

    @Test
    fun testCheckArticleInBackground() {
        if(Platform.getInstance().isMW()) {
            return
        }
        val searchPageObject = SearchPageObjectFactory.get(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.waitForSearchResult("Object-oriented programming language")
        this.backgroundApp(Duration.ofSeconds(3))
        searchPageObject.waitForSearchResult("Object-oriented programming language")
    }
}