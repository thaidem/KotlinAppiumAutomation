package lib.ui.android

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.ui.MyListsPageObject

open class AndroidMyListsPageObject(driver: AppiumDriver<MobileElement>?) : MyListsPageObject(driver)
{
    init
    {
        FOLDER_BY_NAME_TPL = "xpath~//*[@text='{FOLDER_NAME}']"
        ARTICLE_BY_TITLE_TPL = "xpath~//*[@text='{TITLE}']"
    }
}