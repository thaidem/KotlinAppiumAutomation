package lib.ui.android

import lib.ui.NavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

open class AndroidNavigationUI(driver: RemoteWebDriver?) : NavigationUI(driver) {
    init {
        MY_LISTS_LINK = "xpath~//android.widget.FrameLayout[@content-desc='My lists']"
    }
}