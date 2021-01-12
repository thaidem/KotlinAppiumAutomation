package lib.ui.android

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.NavigationUI

open class AndroidNavigationUI(driver: AppiumDriver<MobileElement>?) : NavigationUI(driver)
{
    init
    {
        MY_LISTS_LINK = "xpath~//android.widget.FrameLayout[@content-desc='My lists']"
    }
}