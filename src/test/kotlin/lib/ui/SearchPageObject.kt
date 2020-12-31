package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.SearchPageObject.SearchPageLocators.*

class SearchPageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class SearchPageLocators(val locator: String)
    {
        SEARCH_INIT_ELEMENT("xpath~//*[contains(@text, 'Search Wikipedia')]"),
        SEARCH_INPUT("xpath~//*[contains(@text, 'Search…')]"),
        SEARCH_CANCEL_BUTTON("id~org.wikipedia:id/search_close_btn"),
        SEARCH_RESULT_BY_SUBSTRING_TPL("xpath~//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, '{SUBSTRING}')]"),
        SEARCH_RESULT_ELEMENT("xpath~//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"),
        SEARCH_RESULT_BY_TITLE_TPL("xpath~//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '{TITLE}')]"),
        SEARCH_EMPTY_RESULT_ELEMENT("xpath~//*[contains(@text, 'No results found')]"),
        SEARCH_INPUT_ID("id~org.wikipedia:id/search_src_text"),
        SEARCH_RESULT_LIST("id~org.wikipedia:id/page_list_item_container"),
        SEARCH_RESULT_TITLE_LIST("id~org.wikipedia:id/page_list_item_title"),
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL("xpath~//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '{TITLE}')]/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, '{DESCRIPTION}')]")
    }

    /*TEMPLATES METHODS*/
    private fun getResultSearchElement(substring: String): String
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.locator.replace("{SUBSTRING}", substring)
    }

    private fun getResultSearchElementByTitle(title: String): String
    {
        return SEARCH_RESULT_BY_TITLE_TPL.locator.replace("{TITLE}", title)
    }

    private fun getResultSearchElementByTitleAndDescription(title: String, description: String): String
    {
        val titlePart = SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.locator.replace("{TITLE}", title)
        return titlePart.replace("{DESCRIPTION}", description)
    }
    /*TEMPLATES METHODS*/

    fun initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT.locator,"Cannot find and click search init element",5)
        this.waitForElementPresent(SEARCH_INIT_ELEMENT.locator,"Cannot find search input after clicking search init element", 5)
    }

    fun typeSearchLine(search_line: String)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT.locator, search_line, "Cannot find and type into search input", 5)
    }

    fun inputSearchRequest(request: String)
    {
        initSearchInput()
        typeSearchLine(request)
    }

    fun waitForSearchResult(substring: String)
    {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementPresent(searchResultXpath, "Cannot find search result with substring $substring")
    }

    fun clickByArticleWithSubstring(substring: String)
    {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring $substring", 10)
    }

    fun waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON.locator,"Cannot find search cancel button", 5)
    }

    fun waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON.locator,"Search cancel button is still present", 5)
    }

    fun clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON.locator, "Cannot find and click search cancel button", 5)
    }

    fun getAmountOfFoundArticles(): Int?
    {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT.locator,"Cannot find anything by the request",15
        )
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT.locator)
    }

    fun waitForEmptyResultLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT.locator,"Cannot find empty result element",15)
    }

    fun assertThereIsNotResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT.locator,"We've found some result by request")
    }

    fun assertSearchLineText(textSearchLine: String) {
        this.assertElementHasText(SEARCH_INPUT_ID.locator, textSearchLine,"Element does not contain expected text")
    }

    fun  assertSearchResultListPresent()
    {
        this.assertElementsPresent(SEARCH_RESULT_LIST.locator,"Articles not visible")
    }

    fun  assertSearchResultListNotPresent()
    {
       this.assertElementsNotPresent(SEARCH_RESULT_LIST.locator,"Articles visible")
    }

    fun checkWordsInEachItemsOfSearchResult(request: String)
    {
        this.checkWordsInResult(SEARCH_RESULT_TITLE_LIST.locator, request,"Cannot find request '$request' into each result items",5)
    }

    fun clickByArticleWithTitle(articleTitle: String)
    {
        val searchResultByTitleXpath = getResultSearchElementByTitle(articleTitle)
        this.waitForElementAndClick(searchResultByTitleXpath,"Cannot find article by title '$articleTitle'",5)
    }

    fun  waitForElementByTitleAndDescription(title: String, description: String): Int?
    {
        val searchResultByTitleAndDescriptionXpath = getResultSearchElementByTitleAndDescription(title, description)
        this.waitForElementPresent(searchResultByTitleAndDescriptionXpath,"Cannot find article by title '$title' and description '$description'",10)
        return this.getAmountOfElements(searchResultByTitleAndDescriptionXpath)
    }
}