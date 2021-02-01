package lib.ui

import io.qameta.allure.Step
import lib.Platform
import org.openqa.selenium.remote.RemoteWebDriver

abstract class NavigationUI(driver: RemoteWebDriver?) : MainPageObject(driver)
{
    protected companion object
    {
        var MY_LISTS_LINK = ""
        var OPEN_NAVIGATION = ""
    }

    @Step("Открытие выдвигающегося меню")
    fun openNavigation() {
        if(Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5)
        }else {
            println(
                "Method openNavigation() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    @Step("Клик на пункт меню 'My List'")
    fun clickMyLists() {
        if(Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_LINK, "Cannot find navigation button to My List", 5)
        } else {
            this.waitForElementAndClick(MY_LISTS_LINK,"Cannot find navigation button to My List",5)
        }
    }
}