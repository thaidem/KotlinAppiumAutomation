package lib.ui.mobile_web

import lib.ui.ArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWArticlePageObject(driver: RemoteWebDriver?) : ArticlePageObject(driver) {
    init {
        TITLE = "css~div.page-heading h1"
        FOOTER_ELEMENT = "css~footer"
        OPTIONS_BUTTON = ""
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css~#page-actions a#ca-watch.menu__item--page-actions-watch"
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css~#page-actions a#ca-watch.watched"
        ADD_TO_MY_LIST_OVERLAY = "id~org.wikipedia:id/onboarding_button"
        MY_LIST_NAME_INPUT = "id~org.wikipedia:id/text_input"
        MY_LIST_OK_BUTTON = "xpath~//*[@text='OK']"
        CLOSE_ARTICLE_BUTTON = "xpath~//android.widget.ImageButton[@content-desc='Navigate up']"
        USER_FOLDER_TPL = "xpath~//android.widget.TextView[@text='{FOLDER}']"
    }
}