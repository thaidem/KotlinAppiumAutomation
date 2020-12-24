package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.SearchPageObject.SearchPageLocators
import lib.ui.SearchPageObject.SearchPageLocators.*
import org.openqa.selenium.By

class SearchPageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class SearchPageLocators(val locator: String)
    {
        SEARCH_INIT_ELEMENT("//*[contains(@text, 'Search Wikipedia')]"),
        SEARCH_INPUT("//*[contains(@text, 'Search…')]"),
        SEARCH_CANCEL_BUTTON("org.wikipedia:id/search_close_btn"),
        SEARCH_RESULT_BY_SUBSTRING_TPL("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, '{SUBSTRING}')]"),
        SEARCH_RESULT_ELEMENT("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"),
        SEARCH_EMPTY_RESULT_ELEMENT("//*[contains(@text, 'No results found')]"),
        VALUE_ATTRIBUTE_TEXT_SEARCH_INPUT("org.wikipedia:id/search_src_text")
    }

    /*TEMPLATES METHODS*/
    private fun getResultSearchElement(substring: String): String
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.locator.replace("{SUBSTRING}", substring)
    }
    /*TEMPLATES METHODS*/

    fun initSearchInput()
    {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT.locator),"Cannot find and click search init element",5)
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT.locator),"Cannot find search input after clicking search init element", 5)
    }

    fun typeSearchLine(search_line: String)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT.locator), search_line, "Cannot find and type into search input", 5)
    }

    fun waitForSearchResult(substring: String)
    {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring $substring")
    }

    fun clickByArticleWithSubstring(substring: String)
    {
        val searchResultXpath = getResultSearchElement(substring)
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with substring $substring", 10)
    }

    fun waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON.locator),"Cannot find search cancel button", 5)
    }

    fun waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON.locator),"Search cancel button is still present", 5)
    }

    fun clickCancelSearch()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON.locator), "Cannot find and click search cancel button", 5)
    }

    fun getAmountOfFoundArticles(): Int?
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT.locator),"Cannot find anything by the request",15
        )
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT.locator))
    }

    fun waitForEmptyResultLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT.locator),"Cannot find empty result element",15)
    }

    fun assertThereIsNotResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT.locator),"We've found some result by request")
    }

    fun assertSearchLineText(textSearchLine: String) {
        this.assertElementHasText(By.id(VALUE_ATTRIBUTE_TEXT_SEARCH_INPUT.locator), textSearchLine,"Element does not contain expected text")
    }
}