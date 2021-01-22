package lib.ui

import org.openqa.selenium.remote.RemoteWebDriver

abstract class NavigationUI(driver: RemoteWebDriver?) : MainPageObject(driver)
{
    protected companion object
    {
        var MY_LISTS_LINK = ""

    }

    fun clickMyLists() {
        this.waitForElementAndClick(MY_LISTS_LINK,"Cannot find navigation button 'Me List'",5)
    }
}