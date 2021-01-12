package lib.ui.factories

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import lib.Platform
import lib.ui.ArticlePageObject
import lib.ui.android.AndroidArticlePageObject
import lib.ui.ios.IOSArticlePageObject

open class ArticlePageObjectFactory
{
    companion object {
        fun get(driver: AppiumDriver<MobileElement>?) : ArticlePageObject
        {
            return if(Platform.getInstance().isAndroid()) {
                AndroidArticlePageObject(driver)
            } else {
                IOSArticlePageObject(driver)
            }
        }
    }
}