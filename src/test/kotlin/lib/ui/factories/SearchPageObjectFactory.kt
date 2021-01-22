package lib.ui.factories

import lib.Platform
import lib.ui.SearchPageObject
import lib.ui.android.AndroidSearchPageObject
import lib.ui.ios.IOSSearchPageObject
import lib.ui.mobile_web.MWSearchPageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class SearchPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver?): SearchPageObject {
            return when {
                Platform.getInstance().isAndroid() -> AndroidSearchPageObject(driver)

                Platform.getInstance().isIOS() -> IOSSearchPageObject(driver)

                Platform.getInstance().isMW() -> MWSearchPageObject(driver)

                else -> throw Exception("Unknown platform")
            }
        }
    }
}