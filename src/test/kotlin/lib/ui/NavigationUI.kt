package lib.ui

import lib.Platform
import org.openqa.selenium.remote.RemoteWebDriver

abstract class NavigationUI(driver: RemoteWebDriver?) : MainPageObject(driver)
{
    protected companion object
    {
        var MY_LISTS_LINK = ""
        var OPEN_NAVIGATION = ""
    }

    fun openNavigation() {
        if(Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigatin button", 5)
        }else {
            println(
                "Method openNavigation() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    fun clickMyLists() {
        if(Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_LINK, "Cannot find navigation button to My List", 5)
        } else {
            this.waitForElementAndClick(MY_LISTS_LINK,"Cannot find navigation button to My List",5)
        }
    }
}