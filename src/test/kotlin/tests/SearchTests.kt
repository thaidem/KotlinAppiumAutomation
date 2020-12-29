package tests

import lib.CoreTestCase
import lib.ui.SearchPageObject
import org.junit.Test

class SearchTests : CoreTestCase()
{

    @Test
    fun testSearch()
    {
        val searchPageObject = SearchPageObject(driver)

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Java")
        searchPageObject.waitForSearchResult("Object-oriented programming language")
    }

    @Test
    fun testCancelSearch()
    {
        val searchPageObject = SearchPageObject(driver)

        searchPageObject.initSearchInput()
        searchPageObject.waitForCancelButtonToAppear()
        searchPageObject.clickCancelSearch()
        searchPageObject.waitForCancelButtonToDisappear()
    }

    @Test
    fun testAmountOfNotEmptySearch() {

        val searchPageObject = SearchPageObject(driver)
        val searchLine = "Linkin Park Discography"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(searchLine)
        val amountSearchResult = searchPageObject.getAmountOfFoundArticles()

        kotlin.test.assertTrue(amountSearchResult ?: 0 > 0, "We found too few results!")
    }

    @Test
    fun testAmountOfEmptySearch()
    {
        val searchPageObject = SearchPageObject(driver)
        val searchLine = "asfgdfhgjk43654"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(searchLine)
        searchPageObject.waitForEmptyResultLabel()
        searchPageObject.assertThereIsNotResultOfSearch()
    }

    @Test
    fun testAssertElementHasText()
    {
        val searchPageObject = SearchPageObject(driver)
        val textSearchLine = "Search…"

        searchPageObject.initSearchInput()
        searchPageObject.assertSearchLineText(textSearchLine)
    }

    @Test
    fun testCancelSearchAndCleaningResult()
    {
        val searchPageObject = SearchPageObject(driver)

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("Kotlin")
        searchPageObject.assertSearchResultListPresent()
        searchPageObject.clickCancelSearch()
        searchPageObject.assertSearchResultListNotPresent()
    }

    @Test
    fun testCheckWordsInResult()
    {
        val searchPageObject = SearchPageObject(driver)
        val request = "Kotlin"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine(request)
        searchPageObject.checkWordsInEachItemsOfSearchResult(request)
    }

    @Test
    fun testSearchArticlesByTitleAndDescription()
    {
        val searchPageObject = SearchPageObject(driver)
        val title = "Java"
        val description = "programming"

        searchPageObject.initSearchInput()
        searchPageObject.typeSearchLine("$title $description")
        val amountOfElements = searchPageObject.waitForElementByTitleAndDescription(title, description)
        println(amountOfElements)
        assertTrue("Amount Of Elements less 3", amountOfElements ?: 0 >= 3)
    }
}