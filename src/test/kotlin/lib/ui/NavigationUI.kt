package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

abstract class NavigationUI(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    protected companion object
    {
        var MY_LISTS_LINK = ""

    }

    fun clickMyLists() {
        this.waitForElementAndClick(MY_LISTS_LINK,"Cannot find navigation button 'Me List'",5)
    }
}