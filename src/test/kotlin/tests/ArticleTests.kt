package tests

import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Assert
import org.junit.Test

@Epic("Тесты для статей")
class ArticleTests : CoreTestCase()
{
    @Test
    @Features(Feature("Search"), Feature("Article"))
    @DisplayName("Compare article title with expected one")
    @Description("Мы открываем статью 'Java object-oriented programming language' " +
            "и проверяем, что открыта именно она")
    @Step("Starting testCompareArticleTitle")
    @Severity(SeverityLevel.BLOCKER)
    fun testCompareArticleTitle()
    {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()

        Assert.assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            articleTitle
        )
    }

    @Test
    @Features(Feature("Search"), Feature("Article"), Feature("Swipe"))
    @DisplayName("Swipe article to the footer")
    @Description("Открываем статью и скроллим ее до футера")
    @Step("Starting testSwipeArticle")
    @Severity(SeverityLevel.MINOR)
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
    @Features(Feature("Search"), Feature("Article"))
    @DisplayName("Проверка наличия заголовка в статье")
    @Description("Проверяем, что название статьи в поиске соответствует заголовку в открытой статье")
    @Step("Starting testCheckTitlePresent")
    @Severity(SeverityLevel.NORMAL)
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