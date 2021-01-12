package lib.ui.factories

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.Platform
import lib.ui.NavigationUI
import lib.ui.android.AndroidNavigationUI
import lib.ui.ios.IOSNavigationUI

open class NavigationUIFactory
{
    companion object {
        fun get(driver: AppiumDriver<MobileElement>?) : NavigationUI
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidNavigationUI(driver)
            } else {
                IOSNavigationUI(driver)
            }
        }
    }
}