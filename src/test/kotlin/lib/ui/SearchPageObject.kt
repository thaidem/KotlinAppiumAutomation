package lib.ui

import io.qameta.allure.Step
import org.openqa.selenium.remote.RemoteWebDriver


abstract class SearchPageObject(driver: RemoteWebDriver?) : MainPageObject(driver) {
    protected companion object {
        var SEARCH_INIT_ELEMENT = ""
        var SEARCH_INPUT = ""
        var SEARCH_CANCEL_BUTTON = ""
        var SEARCH_RESULT_BY_SUBSTRING_TPL = ""
        var SEARCH_RESULT_ELEMENT = ""
        var SEARCH_RESULT_BY_TITLE_TPL = ""
        var SEARCH_EMPTY_RESULT_ELEMENT = ""
        var SEARCH_INPUT_ID = ""
        var SEARCH_RESULT_LIST = ""
        var SEARCH_RESULT_TITLE_LIST = ""
        var SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = ""
    }

    /*TEMPLATES METHODS*/
    private fun getResultSearchElement(substring: String): String {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring)
    }

    private fun getResultSearchElementByTitle(title: String): String {
        return SEARCH_RESULT_BY_TITLE_TPL.replace("{TITLE}", title)
    }

    private fun getResultSearchElementByTitleAndDescription(title: String, description: String): String {
        val titlePart = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title)
        return titlePart.replace("{DESCRIPTION}", description)
    }
    /*TEMPLATES METHODS*/

    @Step("Инициализация поля ввода поиска")
    fun initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5)
        this.waitForElementPresent(
            SEARCH_INIT_ELEMENT,
            "Cannot find search input after clicking search init element",
            5
        )
    }

    @Step("Печать поискового запроса '{search_line}' в поле поиска")
    fun typeSearchLine(search_line: String) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5)
    }

    @Step("Ввод поискового запроса '{request}'")
    fun inputSearchRequest(request: String) {
        initSearchInput()
        typeSearchLine(request)
    }

    @Step("Ожидание поискового результата на подстроку '{substring}'")
    fun waitForSearchResult(substring: String) {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementPresent(searchResultXpath, "Cannot find search result with substring $substring")
    }

    @Step("Клик на статью с поисковой подстрокой '{substring}'")
    fun clickByArticleWithSubstring(substring: String) {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementAndClick(
            searchResultXpath,
            "Cannot find and click search result with substring $substring",
            10
        )
    }

    @Step("Ожидание появления кнопки отмены поиска")
    fun waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5)
    }

    @Step("Ожидание исчезновения кнопки отмены поиска")
    fun waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5)
    }

    @Step("Клик на кнопку отмены поиска")
    fun clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5)
    }

    @Step("Получение количества найденных статей")
    fun getAmountOfFoundArticles(): Int? {
        this.waitForElementPresent(
            SEARCH_RESULT_ELEMENT, "Cannot find anything by the request", 15
        )
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT)
    }

    @Step("Ожидание метки пустого результата поиска")
    fun waitForEmptyResultLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15)
    }

    @Step("Утверждение, что результат поиска отсутствует")
    fun assertThereIsNotResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We've found some result by request")
    }

    @Step("Утверждение, что фоновом тексте в поисковой строке присутствует текст '{textSearchLine}'")
    fun assertSearchLineText(textSearchLine: String) {
        this.assertElementHasText(SEARCH_INPUT_ID, textSearchLine, "Element does not contain expected text")
    }

    @Step("Утверждение, что список результата поиска не пустой")
    fun assertSearchResultListPresent() {
        this.assertElementsPresent(SEARCH_RESULT_LIST, "Articles not visible")
    }

    @Step("Утверждение, что список результата поиска пустой")
    fun assertSearchResultListNotPresent() {
        this.assertElementsNotPresent(SEARCH_RESULT_LIST, "Articles visible")
    }

    @Step("Проверка поискового результата на наличие поиского запроса '{request}'")
    fun checkWordsInEachItemsOfSearchResult(request: String) {
        this.checkWordsInResult(
            SEARCH_RESULT_TITLE_LIST,
            request,
            "Cannot find request '$request' into each result items",
            5
        )
    }

    @Step("Клик на статью с заголовком '{articleTitle}'")
    fun clickByArticleWithTitle(articleTitle: String) {
        val searchResultByTitleXpath = getResultSearchElementByTitle(articleTitle)
        this.waitForElementAndClick(searchResultByTitleXpath, "Cannot find article by title '$articleTitle'", 5)
    }

    @Step("Поиск статей по заголовку '{title}' и описанию '{description}'")
    fun waitForElementByTitleAndDescription(title: String, description: String): Int? {
        val searchResultByTitleAndDescriptionXpath = getResultSearchElementByTitleAndDescription(title, description)
        this.waitForElementPresent(
            searchResultByTitleAndDescriptionXpath,
            "Cannot find article by title '$title' and description '$description'",
            10
        )
        return this.getAmountOfElements(searchResultByTitleAndDescriptionXpath)
    }
}