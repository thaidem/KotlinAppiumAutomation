package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By

class NavigationUI(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver)
{
    enum class NavigationLocators(val locator: String)
    {
        MY_LISTS_LINK("//android.widget.FrameLayout[@content-desc='My lists']")

    }

    fun clickMyLists() {
        this.waitForElementAndClick(
            By.xpath( NavigationLocators.MY_LISTS_LINK.locator),
            "Cannot find navigation button 'Me List'",
            5)
    }
}