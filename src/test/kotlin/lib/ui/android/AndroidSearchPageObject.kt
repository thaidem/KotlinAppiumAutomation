package lib.ui.android

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.SearchPageObject

open class AndroidSearchPageObject(driver: AppiumDriver<MobileElement>?) : SearchPageObject(driver)
{
    init
    {
        SEARCH_INIT_ELEMENT = "xpath~//*[contains(@text, 'Search Wikipedia')]"
        SEARCH_INPUT = "xpath~//*[contains(@text, 'Search…')]"
        SEARCH_CANCEL_BUTTON = "id~org.wikipedia:id/search_close_btn"
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath~//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, '{SUBSTRING}')]"
        SEARCH_RESULT_ELEMENT = "xpath~//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"
        SEARCH_RESULT_BY_TITLE_TPL = "xpath~//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '{TITLE}')]"
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath~//*[contains(@text, 'No results found')]"
        SEARCH_INPUT_ID = "id~org.wikipedia:id/search_src_text"
        SEARCH_RESULT_LIST = "id~org.wikipedia:id/page_list_item_container"
        SEARCH_RESULT_TITLE_LIST = "id~org.wikipedia:id/page_list_item_title"
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath~//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '{TITLE}')]/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and contains(@text, '{DESCRIPTION}')]"
    }
}