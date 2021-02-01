package tests

import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.Platform
import lib.ui.AuthorizationPageObject
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.MyListsPageObjectFactory
import lib.ui.factories.NavigationUIFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Assert
import org.junit.Test

@Epic("Тесты для работы с 'My List'")
class MyListsTests : CoreTestCase() {
    @Test
    @Features(Feature("Search"), Feature("Save Article to My List"), Feature("Swipe"))
    @DisplayName("Добавление и удаление статей в(из) избранное(ого)")
    @Description("Добавляем 2 найденные статьи в избранное, удаляем одну, проверяам, что осталась одна статья в избранном")
    @Step("Starting testSaveTwoArticlesAndDeleteOneArticle")
    @Severity(SeverityLevel.NORMAL)
    fun testSaveTwoArticlesAndDeleteOneArticle() {

        val request = "Java"
        val articleTitle1 = "JavaScript"
        val articleTitle2 = "Java (programming language)"
        val nameFolder = "Learning programming"
        val login = "dementyK"
        val password = "dementy19"
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val articlePageObject = ArticlePageObjectFactory.get(driver)

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle1)
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameFolder)
        } else {
            articlePageObject.addArticleToMySaved()
        }
        if (Platform.getInstance().isMW()) {
            val auth = AuthorizationPageObject(driver)
            auth.clickButton()
            auth.enterLoginData(login, password)
            auth.submitForm()

            articlePageObject.waitForTitleElement()
            Assert.assertEquals("We are not on the same page after login",
                articleTitle1,
                articlePageObject.getArticleTitle())
            articlePageObject.addArticleToMySaved()
        }
        articlePageObject.closeArticle()

        searchPageObject.inputSearchRequest(request)
        searchPageObject.clickByArticleWithTitle(articleTitle2)
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameFolder)
        } else {
            articlePageObject.addArticleToMySaved()
        }
        articlePageObject.closeArticle()

        val navigationUI = NavigationUIFactory.get(driver)
        navigationUI.openNavigation()
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObjectFactory.get(driver)
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameFolder)
        }
        myListsPageObject.swipeByArticleToDelete(articleTitle1)

        myListsPageObject.openArticle(articleTitle2)
        if (Platform.getInstance().isAndroid()) {
            val openedArticleTitle = articlePageObject.getArticleTitle()
            Assert.assertEquals("Article title have been changed after open", articleTitle2, openedArticleTitle)
        } else {
            val urlOpenedArticle = articlePageObject.getURLArticle()
            println(urlOpenedArticle)
            Assert.assertTrue(urlOpenedArticle?.contains(articleTitle2) == true)
        }
    }

    @Test
    @Features(Feature("Search"), Feature("Save Article to My List"))
    @DisplayName("Добавление статьи в избранное")
    @Description("Добавляем статью в избранное")
    @Step("Starting testSaveFirstArticleToMyList")
    @Severity(SeverityLevel.NORMAL)
    fun testSaveFirstArticleToMyList() {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val nameFolder = "Learning programming"
        val login = "dementyK"
        val password = "dementy19"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language")

        val articlePageObject = ArticlePageObjectFactory.get(driver)
        articlePageObject.waitForTitleElement()
        val articleTitle = articlePageObject.getArticleTitle()
        println(articleTitle)

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameFolder)
        } else {
            articlePageObject.addArticleToMySaved()
        }

        if (Platform.getInstance().isMW()) {
            val auth = AuthorizationPageObject(driver)
            auth.clickButton()
            auth.enterLoginData(login, password)
            auth.submitForm()

            articlePageObject.waitForTitleElement()
            Assert.assertEquals("We are not on the same page after login",
                articleTitle,
                articlePageObject.getArticleTitle())
            articlePageObject.addArticleToMySaved()
        }
        articlePageObject.closeArticle()

        val navigationUI = NavigationUIFactory.get(driver)
        navigationUI.openNavigation()
        navigationUI.clickMyLists()

        val myListsPageObject = MyListsPageObjectFactory.get(driver)

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameFolder)
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle.toString())
    }
}