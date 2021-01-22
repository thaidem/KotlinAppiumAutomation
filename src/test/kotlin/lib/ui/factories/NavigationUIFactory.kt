package lib.ui.factories

import lib.Platform
import lib.ui.NavigationUI
import lib.ui.android.AndroidNavigationUI
import lib.ui.ios.IOSNavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

open class NavigationUIFactory
{
    companion object {
        fun get(driver: RemoteWebDriver?) : NavigationUI
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidNavigationUI(driver)
            } else {
                IOSNavigationUI(driver)
            }
        }
    }
}