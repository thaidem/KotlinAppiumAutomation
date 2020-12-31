package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.NavigationUI.NavigationLocators.MY_LISTS_LINK

class NavigationUI(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class NavigationLocators(val locator: String)
    {
        MY_LISTS_LINK("xpath~//android.widget.FrameLayout[@content-desc='My lists']")

    }

    fun clickMyLists() {
        this.waitForElementAndClick(MY_LISTS_LINK.locator,"Cannot find navigation button 'Me List'",5)
    }
}