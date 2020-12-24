import lib.CoreTestCase
import lib.ui.MainPageObject
import lib.ui.SearchPageObject
import org.junit.Test
import org.openqa.selenium.By

class FirstTest : CoreTestCase() {

    private var mainPageObject: MainPageObject? = null

    override fun setUp()
    {
        super.setUp()
        mainPageObject = MainPageObject(driver)
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
    fun testCancelSearchAndCleaningResult() {
        mainPageObject?.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        mainPageObject?.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Kotlin",
            "Cannot find search input",
            5
        )

        mainPageObject?.assertElementsPresent(
            By.id("org.wikipedia:id/page_list_item_container"),
            "Articles not visible"
        )

        mainPageObject?.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X cancel search",
            5
        )

        mainPageObject?.assertElementsNotPresent(
            By.id("org.wikipedia:id/page_list_item_container"),
            "Articles visible"
        )
    }

    @Test
    fun testCheckWordsInResult() {
        mainPageObject?.waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5
        )

        mainPageObject?.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Kotlin",
            "Cannot find search input",
            5
        )

        mainPageObject?.checkWordsInResult(
            By.id("org.wikipedia:id/page_list_item_title"),
            "Kotlin",
            "No items with this locator found",
            5
        )
    }


    @Test
    fun testSaveTwoArticlesAndDeleteOneArticle() {

        val request = "Java"
        val articleTitle1 = "JavaScript"
        val articleTitle2 = "Java (programming language)"
        val nameFolder = "Learning programming"

        mainPageObject?.inputSearchRequest(request)
        mainPageObject?.selectAndSaveArticle(articleTitle1, nameFolder)

        mainPageObject?.inputSearchRequest(request)
        mainPageObject?.selectAndSaveArticle(articleTitle2, nameFolder)

        mainPageObject?.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button 'Me List'",
            5
        )

        mainPageObject?.waitForElementAndClick(
            By.xpath("//*[@text='$nameFolder']"),
            "Cannot find created folder",
            25
        )

        mainPageObject?.swipeElementToLeft(
            By.xpath("//*[@text='$articleTitle1']"),
            "Cannot find saved article '$articleTitle1'"
        )

        mainPageObject?.waitForElementNotPresent(
            By.xpath("//*[@text='$articleTitle1']"),
            "Cannot delete saved article '$articleTitle1'",
            15
        )

        mainPageObject?.waitForElementAndClick(
            By.xpath("//*[@text='$articleTitle2']"),
            "Cannot find saved article '$articleTitle2'",
            5
        )

        val openedArticleTitle  = mainPageObject?.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
        )

        assertEquals(
            "Article title have been changed after open",
            articleTitle2,
            openedArticleTitle)
    }

    @Test
    fun testCheckTitlePresent() {
        val request = "Java"
        val articleTitle = "Java (programming language)"

        mainPageObject?.inputSearchRequest(request)

        mainPageObject?.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '$articleTitle')]"),
            "Cannot find article",
            5
        )

        mainPageObject?.assertElementsPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title"
        )
    }

}