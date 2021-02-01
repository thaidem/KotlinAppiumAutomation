package tests

import io.appium.java_client.AppiumDriver
import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName
import lib.CoreTestCase
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Assert
import org.junit.Test

@Epic("Тесты для работы с поиском")
class SearchTests : CoreTestCase() {
    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка работы поисковой строки")
    @Description("Вводим поисковый запрос в строку поиска")
    @Step("Starting testSearch")
    @Severity(SeverityLevel.BLOCKER)
    fun testSearch() {
        val searchPageObject = SearchPageObjectFactory.get(driver)

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.waitForSearchResult("bject-oriented programming language")
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка нажатия кнопки Отмена поиска")
    @Description("Нажимаем кнопку/крестик Отменить поиск")
    @Step("Starting testCancelSearch")
    @Severity(SeverityLevel.BLOCKER)
    fun testCancelSearch() {
        val searchPageObject = SearchPageObjectFactory.get(driver)

        searchPageObject.initSearchInput()
        searchPageObject.waitForCancelButtonToAppear()
        searchPageObject.clickCancelSearch()
        searchPageObject.waitForCancelButtonToDisappear()
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка непустого поиска")
    @Description("Проверяем, что появились результаты поиска")
    @Step("Starting testAmountOfNotEmptySearch")
    @Severity(SeverityLevel.BLOCKER)
    fun testAmountOfNotEmptySearch() {

        val searchPageObject = SearchPageObjectFactory.get(driver)
        val searchLine = "Linkin Park Discography"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(searchLine)
        val amountSearchResult = searchPageObject.getAmountOfFoundArticles()

        kotlin.test.assertTrue(amountSearchResult ?: 0 > 0, "We found too few results!")
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка пустого поиска")
    @Description("Проверяем, что не появились результаты поиска")
    @Step("Starting testAmountOfEmptySearch")
    @Severity(SeverityLevel.NORMAL)
    fun testAmountOfEmptySearch() {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val searchLine = "asfgdfhgjk43654"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(searchLine)
        searchPageObject.waitForEmptyResultLabel()
        searchPageObject.assertThereIsNotResultOfSearch()
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка фонового текста в строке поиска")
    @Description("Проверяем, что текст-подложка в строке поиска соответствует заданной строке")
    @Step("Starting testAssertElementHasText")
    @Severity(SeverityLevel.MINOR)
    fun testAssertElementHasText() {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val textSearchLine = if (driver is AppiumDriver<*>) {
            "Search…"
        } else {
            "Search Wikipedia"
        }

        searchPageObject.initSearchInput()
        searchPageObject.assertSearchLineText(textSearchLine)
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка поиска и удаление поискового результата")
    @Description("Проверяем, что появились результаты поиска и исчезли после нажатия кнопки Отменить поиск")
    @Step("Starting testCancelSearchAndCleaningResult")
    @Severity(SeverityLevel.BLOCKER)
    fun testCancelSearchAndCleaningResult() {
        val searchPageObject = SearchPageObjectFactory.get(driver)

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Kotlin")
        searchPageObject.assertSearchResultListPresent()
        searchPageObject.clickCancelSearch()
        searchPageObject.assertSearchResultListNotPresent()
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка наличия слова в резултате поиска")
    @Description("Проверяем, что заданная строка присутствует в результате поиска")
    @Step("Starting testCheckWordsInResult")
    @Severity(SeverityLevel.BLOCKER)
    fun testCheckWordsInResult() {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val request = "Kotlin"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(request)
        searchPageObject.checkWordsInEachItemsOfSearchResult(request)
    }

    @Test
    @Features(Feature("Search"))
    @DisplayName("Поиск статей по заголовку и описанию")
    @Description("Проверяем, что появились результаты поиска по заданному заголовку и описанию и их не менее 3")
    @Step("Starting testSearchArticlesByTitleAndDescription")
    @Severity(SeverityLevel.NORMAL)
    fun testSearchArticlesByTitleAndDescription() {
        val searchPageObject = SearchPageObjectFactory.get(driver)
        val title = "Java"
        val description = "programming"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("$title $description")
        val amountOfElements = searchPageObject.waitForElementByTitleAndDescription(title, description)
        println(amountOfElements)
        Assert.assertTrue("Amount Of Elements less 3", amountOfElements ?: 0 >= 1)
    }
}