package lib.ui.factories

import lib.Platform
import lib.ui.MyListsPageObject
import lib.ui.android.AndroidMyListsPageObject
import lib.ui.ios.IOSMyListsPageObject
import lib.ui.mobile_web.MWMyListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class MyListsPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver?): MyListsPageObject {
            return when {
                Platform.getInstance().isAndroid() -> AndroidMyListsPageObject(driver)

                Platform.getInstance().isIOS() -> IOSMyListsPageObject(driver)

                Platform.getInstance().isMW() -> MWMyListsPageObject(driver)

                else -> throw Exception("Unknown platform")
            }
        }
    }
}