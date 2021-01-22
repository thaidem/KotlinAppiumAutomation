package lib.ui.factories

import lib.Platform
import lib.ui.MyListsPageObject
import lib.ui.android.AndroidMyListsPageObject
import lib.ui.ios.IOSMyListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class MyListsPageObjectFactory
{
    companion object {
        fun get(driver: RemoteWebDriver?) : MyListsPageObject
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidMyListsPageObject(driver)
            } else {
                IOSMyListsPageObject(driver)
            }
        }
    }
}