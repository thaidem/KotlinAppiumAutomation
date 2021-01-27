package lib.ui.mobile_web

import lib.ui.ArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWArticlePageObject(driver: RemoteWebDriver?) : ArticlePageObject(driver) {
    init {
        TITLE = "css~div.page-heading h1"
        FOOTER_ELEMENT = "css~footer"
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css~#page-actions a#ca-watch.menu__item--page-actions-watch"
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css~#page-actions a#ca-watch.watched"
    }
}