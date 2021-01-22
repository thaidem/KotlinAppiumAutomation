package lib.ui.factories

import lib.Platform
import lib.ui.ArticlePageObject
import lib.ui.android.AndroidArticlePageObject
import lib.ui.ios.IOSArticlePageObject
import lib.ui.mobile_web.MWArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class ArticlePageObjectFactory{
    companion object {
        fun get(driver: RemoteWebDriver?): ArticlePageObject {
            return when {
                Platform.getInstance().isAndroid() -> AndroidArticlePageObject(driver)

                Platform.getInstance().isIOS() -> IOSArticlePageObject(driver)

                Platform.getInstance().isMW() -> MWArticlePageObject(driver)

                else -> throw Exception("Unknown platform")
            }
        }
    }
}
