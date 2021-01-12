package lib.ui.factories

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.Platform
import lib.ui.SearchPageObject
import lib.ui.android.AndroidSearchPageObject
import lib.ui.ios.IOSSearchPageObject

open class SearchPageObjectFactory
{
    companion object {
        fun get(driver: AppiumDriver<MobileElement>?) : SearchPageObject
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidSearchPageObject(driver)
            } else {
                IOSSearchPageObject(driver)
            }
        }
    }
}