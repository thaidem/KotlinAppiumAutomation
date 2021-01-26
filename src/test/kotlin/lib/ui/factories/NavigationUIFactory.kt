package lib.ui.factories

import lib.Platform
import lib.ui.NavigationUI
import lib.ui.android.AndroidNavigationUI
import lib.ui.ios.IOSNavigationUI
import lib.ui.mobile_web.MWNavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

open class NavigationUIFactory {
    companion object {
        fun get(driver: RemoteWebDriver?): NavigationUI {
            return when {
                Platform.getInstance().isAndroid() -> AndroidNavigationUI(driver)

                Platform.getInstance().isIOS() -> IOSNavigationUI(driver)

                Platform.getInstance().isMW() -> MWNavigationUI(driver)

                else -> throw Exception("Unknown platform")
            }
        }
    }
}