package lib.ui.mobile_web

import lib.ui.SearchPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWSearchPageObject(driver: RemoteWebDriver?) : SearchPageObject(driver) {
    init {
        SEARCH_INIT_ELEMENT = "css~button#searchIcon"
        SEARCH_INPUT = "css~form>input[type='search']"
        SEARCH_CANCEL_BUTTON = "css~div.header-action>button.cancel"
        SEARCH_RESULT_BY_SUBSTRING_TPL =
            "xpath~//div[contains(@class, 'wikidata-description')][contains(text(),'{SUBSTRING}')]"
        SEARCH_RESULT_ELEMENT = "css~ul.page-list>li.page-summary"
        SEARCH_RESULT_BY_TITLE_TPL = "xpath~//a[contains(@data-title,'{TITLE}')]"
        SEARCH_EMPTY_RESULT_ELEMENT = "css~p.without-results"
        SEARCH_INPUT_ID = "css~form>input"
        SEARCH_RESULT_LIST = "xpath~*//ul[contains(@class,'page-list')]/child::li[contains(@class,'page-summary')]"
        SEARCH_RESULT_TITLE_LIST = "xpath~*//ul[contains(@class,'page-list')]/child::li[contains(@class,'page-summary')]"
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
            "xpath~a[contains(@class,'title')]/child::h3[contains(text(),'Java')] | //a[contains(@class,'title')]/child::div[contains(@class,'wikidata-description') and contains(text(),'{DESCRIPTION}')]"
    }
}