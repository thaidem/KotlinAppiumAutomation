package lib.ui.factories

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.Platform
import lib.ui.MyListsPageObject
import lib.ui.android.AndroidMyListsPageObject
import lib.ui.ios.IOSMyListsPageObject

open class MyListsPageObjectFactory
{
    companion object {
        fun get(driver: AppiumDriver<MobileElement>?) : MyListsPageObject
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidMyListsPageObject(driver)
            } else {
                IOSMyListsPageObject(driver)
            }
        }
    }
}